package com.donkeys_today.server.support.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "app")
@EnableConfigurationProperties(ReplyProperty.class)
@PropertySource(value = "classpath:reply.yml", factory = YamlPropertySourceFactory.class)
@Configuration
public class ReplyProperty {

  private String comment;

}
