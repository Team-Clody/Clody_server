package com.donkeys_today.server.support.config;

import java.util.Objects;
import java.util.Properties;
import javax.annotation.Nullable;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.util.StringUtils;

public class YamlPropertySourceFactory implements PropertySourceFactory {

  @Override
  public PropertySource<?> createPropertySource(@Nullable String name, EncodedResource resource) {
    Properties yamlProperties = loadYamlProperties(resource);
    String sourceName = StringUtils.hasText(name) ? name : resource.getResource().getFilename();
    return new PropertiesPropertySource(Objects.requireNonNull(sourceName), Objects.requireNonNull(yamlProperties));
  }

  private Properties loadYamlProperties(EncodedResource resource) {
    YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
    factory.setResources(resource.getResource());
    return factory.getObject();
  }
}
