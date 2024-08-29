//package com.clody.clodybatch.config;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.job.SimpleJob;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@RequiredArgsConstructor
//@Slf4j
//public class JobConfig {
//
//  @Bean
//  public Job myJob(JobRepository jobRepository, Step step){
//    SimpleJob simpleJob = new SimpleJob();
//    return new JobBuilder("myHib",jobRepository)
//        .start(step)
//        .build();
//
//  }
//
//}
