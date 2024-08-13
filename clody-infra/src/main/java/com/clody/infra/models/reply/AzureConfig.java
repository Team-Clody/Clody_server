package com.clody.infra.models.reply;

import com.azure.ai.openai.assistants.AssistantsClient;
import com.azure.ai.openai.assistants.AssistantsClientBuilder;
import com.azure.ai.openai.assistants.AssistantsServiceVersion;
import com.azure.ai.openai.assistants.models.Assistant;
import com.azure.ai.openai.assistants.models.AssistantCreationOptions;
import com.azure.core.credential.AzureKeyCredential;
import com.clody.infra.config.property.InstructionProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class AzureConfig {

  private final InstructionProperty instructionProperty;

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
  public Assistant customAssistant(AssistantsClient client) {
    return client.createAssistant(new AssistantCreationOptions(model)
        .setName("로디")
        .setInstructions(instructionProperty.getContent()));
  }

}
