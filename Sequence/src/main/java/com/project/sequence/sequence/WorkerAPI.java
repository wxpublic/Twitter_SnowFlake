package com.project.sequence.sequence;

import com.project.sequence.component.WorkerID;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * PACKAGE      :  com.project.sequence.sequence
 * CREATE DATE  :  16/9/8
 * AUTHOR       :  xiaoyi.xie
 * 文件描述      :  Worker Sequence
 */
@RestController
@RequestMapping("/api/worker")
public class WorkerAPI {

    @Resource
    private WorkerID workerID;

    /**
     * 获取Worker Sequence
     * @param num Sequence数量
     * @return
     */
    @RequestMapping(value = "/sequence",method = RequestMethod.GET)
    public List<Long> sequence(@RequestParam(value="num",required=false,defaultValue ="1" )Long num){
        List<Long> sequences=new ArrayList<Long>();
        for(int i=0;i<num;i++){
            sequences.add(workerID.nextId());
        }
        return sequences;
    }

}