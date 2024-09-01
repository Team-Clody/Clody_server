//package com.clody.clodybatch.config;
//
//import jakarta.annotation.PostConstruct;
//import java.util.Map;
//import lombok.RequiredArgsConstructor;
//import org.springframework.batch.core.Job;
//import org.springframework.context.ApplicationContext;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class Inspector {
//
//  private final ApplicationContext applicationContext;
//
//  @PostConstruct
//  public void printAllJobs() {
//    Map<String, Job> jobs = applicationContext.getBeansOfType(Job.class);
//    System.out.println("Registered Jobs:");
//    jobs.forEach((name, job) -> System.out.println("Job name: " + name));
//  }
//}
