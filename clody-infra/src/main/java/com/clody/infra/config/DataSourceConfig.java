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
    HikariConfig config = new HikariConfig();

    config.setDriverClassName("org.postgresql.Driver");
    config.setJdbcUrl("jdbc:postgresql://localhost:5432/donkey_meta");
    config.setUsername("hyunw9");
    config.setPassword("1228");
    System.out.println("DriverClassName: " + config.getDriverClassName());
    System.out.println("JdbcUrl: " + config.getJdbcUrl());
    return config;
  }

  @Primary
  @Bean(META_DATASOURCE)
  public DataSource metaDataSource(){
    return new LazyConnectionDataSourceProxy(new HikariDataSource(metaHikariConfig()));
  }

  @Bean
  @ConfigurationProperties(prefix = "spring.datasource.domain.hikari")
  public HikariConfig domainHikariConfig() {
    HikariConfig config = new HikariConfig();
    config.setDriverClassName("org.postgresql.Driver");
    config.setJdbcUrl("jdbc:postgresql://localhost:5432/donkey_local");
    config.setUsername("hyunw9");
    config.setPassword("1228");
    return config;
  }

  @Bean(DOMAIN_DATASOURCE)
  public DataSource domainDataSource(){
    return new LazyConnectionDataSourceProxy(new HikariDataSource(domainHikariConfig()));
  }


}
