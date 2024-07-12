package com.donkeys_today.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DonkeyServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(DonkeyServerApplication.class, args);
  }

}
