package com.clody.clodyapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
    "com.example.infrastructure", // 스캔하려는 다른 모듈의 패키지
    "com.example.domain"        // 추가로 스캔할 패키지
})
public class ClodyApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(ClodyApiApplication.class, args);
  }

}
