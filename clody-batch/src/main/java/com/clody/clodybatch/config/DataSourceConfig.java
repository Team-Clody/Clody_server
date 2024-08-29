package com.clody.clodybatch.config;

import javax.sql.DataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {

  @Bean(name = "domainDataSource")
  @ConfigurationProperties(prefix = "spring.datasource.domain.hikari")
  public DataSource domainDataSource(){
    return DataSourceBuilder.create().build();
  }

  @Bean(name = "domainDataSource")
  @ConfigurationProperties(prefix = "spring.datasource.meta.hikari")
  public DataSource metaDataSource(){
    return DataSourceBuilder.create().build();
  }
}
