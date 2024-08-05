package com.clody.clodyapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.ActiveProfiles;

@SpringBootApplication
@ActiveProfiles(profiles = {"local"})
class ClodyApiApplicationTests {

  @Test
  void contextLoads() {
  }

}
