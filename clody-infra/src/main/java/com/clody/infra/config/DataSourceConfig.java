package com.clody.infra.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

@Configuration
public class DataSourceConfig {

  public static final String DOMAIN_DATASOURCE = "clody_local";
  public static final String META_DATASOURCE = "clody_meta";

  @Bean
  @ConfigurationProperties(prefix = "spring.datasource.meta.hikari")
  public HikariConfig metaHikariConfig() {
    return new HikariConfig();
  }

  @Primary
  @Bean(META_DATASOURCE)
  public DataSource metaDataSource(){
    return new LazyConnectionDataSourceProxy(new HikariDataSource(metaHikariConfig()));
  }

  @Bean
  @ConfigurationProperties(prefix = "spring.datasource.domain.hikari")
  public HikariConfig businessHikariConfig() {
    return new HikariConfig();
  }

  @Bean(DOMAIN_DATASOURCE)
  public DataSource businessDataSource(){
    return new LazyConnectionDataSourceProxy(new HikariDataSource(businessHikariConfig()));
  }


}
