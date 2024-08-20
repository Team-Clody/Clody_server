package com.clody.infra.config;

import com.azure.ai.openai.assistants.AssistantsClient;
import com.azure.ai.openai.assistants.AssistantsClientBuilder;
import com.azure.ai.openai.assistants.AssistantsServiceVersion;
import com.azure.ai.openai.assistants.models.Assistant;
import com.azure.ai.openai.assistants.models.AssistantCreationOptions;
import com.azure.core.credential.AzureKeyCredential;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
@Slf4j
public class AzureConfig {
  
  @Value("${azure.credential}")
  private String credentialKey;
  @Value("${azure.endpoint}")
  private String endpoint;
  @Value("${azure.key}")
  private String key;
  @Value("${azure.model}")
  private String model;

  @Bean
  AssistantsClient assistantsClient() {
    return new AssistantsClientBuilder()
        .credential(new AzureKeyCredential(key))
        .serviceVersion(AssistantsServiceVersion.getLatest())
        .endpoint(endpoint)
        .buildClient();
  }

  @Bean
  public Assistant customAssistant(AssistantsClient client) throws IOException {
    String instructions = loadInstructionsFromFile("instruction.txt");
    log.info("Application Started with Instructions: {}", instructions);
    return client.createAssistant(new AssistantCreationOptions(model)
        .setName("로디")
        .setInstructions(instructions));
  }

  private String loadInstructionsFromFile(String filePath) throws IOException {
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(
        Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream(filePath)), StandardCharsets.UTF_8))) {
      return reader.lines().collect(Collectors.joining("\n"));
    }
  }

}
