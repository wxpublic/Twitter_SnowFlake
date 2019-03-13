#分布式高效序列(sequence)

##简介
高效GUID产生算法(sequence),基于Snowflake实现64位自增ID算法。

Twitter Sequence

##Snowflake算法核心
把时间戳，工作机器id，序列号组合在一起。

![Snowflake算法核心](docs/snowflake-64bit.jpg)

除了最高位bit标记为不可用以外，其余三组bit占位均可浮动，看具体的业务需求而定。默认情况下41bit的时间戳可以支持该算法使用到2082年，10bit的工作机器id可以支持1023台机器，序列号支持1毫秒产生4095个自增序列id。下文会具体分析。

##Snowflake – 时间戳
这里时间戳的细度是毫秒级，具体代码如下，建议使用64位linux系统机器.

##Snowflake – 工作机器id
严格意义上来说这个bit段的使用可以是进程级，机器级的话你可以使用MAC地址来唯一标示工作机器，工作进程级可以使用IP+Path来区分工作进程。如果工作机器比较少，可以使用配置文件来设置这个id是一个不错的选择，如果机器过多配置文件的维护是一个灾难性的事情。

##Snowflake – 序列号
序列号就是一系列的自增id（多线程建议使用atomic），为了处理在同一毫秒内需要给多条消息分配id，若同一毫秒把序列号用完了，则“等待至下一毫秒”。


Worker Sequence(类似淘宝订单号)
原理和Twitter一致,Sequence位数少二位

http://darktea.github.io/notes/2013/12/08/Unique-ID
http://itindex.net/detail/55595-%E5%94%AF%E4%B8%80-%E5%BA%8F%E5%88%97
http://www.importnew.com/22211.html