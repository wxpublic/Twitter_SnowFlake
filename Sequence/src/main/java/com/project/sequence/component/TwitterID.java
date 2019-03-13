package com.project.sequence.component;

import com.project.sequence.common.SystemClock;

/**
 * PACKAGE      :  com.project.sequence.component
 * CREATE DATE  :  16/9/8
 * AUTHOR       :  xiaoyi.xie
 * 文件描述      :  Twitter Sequence
 */
public class TwitterID {

    /**
     * 0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 - 000000000000
     * 第一位为未使用，接下来的41位为毫秒级时间(41位的长度可以使用69年)，然后是5位datacenterId和5位workerId(10位的长度最多支持部署1024个节点） ，
     * 最后12位是毫秒内的计数（12位的计数顺序号支持每个节点每毫秒产生4096个ID序号）
     * 一共加起来刚好64位，为一个Long型。(转换成字符串长度为18)
     * snowflake生成的ID整体上按照时间自增排序，并且整个分布式系统内不会产生ID碰撞
     * （由datacenter和workerId作区分），并且效率较高。
     */

    // 基准时间
    private final long twepoch =1483228800000L;//2017-01-01 00:00:00 GMT
    //机器标识位数
    private final long workerIdBits = 5L;
    //数据中心位数
    private final long datacenterIdBits = 5L;
    //序列号识位数
    private final long sequenceBits = 12L;
    //机器标识最大值
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);
    //数据中心最大值
    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
    //序列号识最大值
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);
    //机器ID左移
    private final long workerIdShift = sequenceBits;
    //数据中心左移
    private final long datacenterIdShift = sequenceBits + workerIdBits;
    //时间毫秒左移
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;


    private long workerId;
    private long datacenterId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    /**
     * @param workerId      工作机器ID(0-31)
     * @param datacenterId  序列号(0-31)
     */
    public TwitterID(final long workerId,final long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(
                    String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(
                    String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    /**
     * 获取下一个ID
     * @return
     */
    public synchronized long nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format(
                    "Clock moved backwards. Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        if (lastTimestamp == timestamp) {
            //当前毫秒内，则+1
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                //当前毫秒内计数满了，则等待下一秒
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        return ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift)
                | (workerId << workerIdShift) | sequence;
    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    protected long timeGen() {
        return SystemClock.now();
    }

}