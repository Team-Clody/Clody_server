package com.clody.support.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "app")
@EnableConfigurationProperties(ReplyProperty.class)
@PropertySource(value = "classpath:reply.yaml", factory = YamlPropertySourceFactory.class)
public class ReplyProperty {

  private final String content;
}
