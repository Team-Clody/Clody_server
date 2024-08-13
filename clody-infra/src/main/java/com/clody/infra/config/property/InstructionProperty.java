package com.clody.infra.config.property;

import com.clody.domain.config.YamlPropertySourceFactory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "set")
@EnableConfigurationProperties(InstructionProperty.class)
@PropertySource(value = "classpath:instruction.yaml", factory = YamlPropertySourceFactory.class)
public class InstructionProperty {

  private final String content;
}
