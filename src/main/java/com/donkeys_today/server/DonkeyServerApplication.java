package com.donkeys_today.server;

import com.donkeys_today.server.support.config.ReplyProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
//@EnableScheduling
@EnableConfigurationProperties(ReplyProperty.class)
public class DonkeyServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(DonkeyServerApplication.class, args);
  }

}
