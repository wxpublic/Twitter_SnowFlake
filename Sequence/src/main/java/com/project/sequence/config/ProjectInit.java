package com.project.sequence.config;

import com.project.sequence.component.TwitterID;
import com.project.sequence.component.WorkerID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * PACKAGE      :  com.project.sequence.config
 * CREATE DATE  :  16/9/8
 * AUTHOR       :  xiaoyi.xie
 * 文件描述      :  项目初始化
 */
@Configuration
public class ProjectInit {

    @Bean
    public WorkerID workerID(@Value("${workerID.machineID}")long machineID){
        return new WorkerID(machineID);
    }

    @Bean
    public TwitterID twitterID(@Value("${twitterID.machineID}")long machineID,
                               @Value("${twitterID.datacenterID}")long datacenterID){
        return new TwitterID(machineID,datacenterID);
    }

}