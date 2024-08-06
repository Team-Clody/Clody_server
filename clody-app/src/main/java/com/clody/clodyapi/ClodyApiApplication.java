package com.clody.clodyapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.clody.domain", "com.clody.infra","com.clody.support","com.clody"})
public class ClodyApiApplication {

  public static void main(String[] args) {
    System.setProperty("spring.config.name","application,application-infrastructure,application-domain,application-support,application-api");
    SpringApplication.run(ClodyApiApplication.class, args);
  }
}
