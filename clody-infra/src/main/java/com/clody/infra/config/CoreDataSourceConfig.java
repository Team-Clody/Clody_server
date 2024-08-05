//package com.clody.infra.config;
//
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class CoreDataSourceConfig {
//
//  @Bean
//  @ConfigurationProperties(prefix = "spring.datasource")
//  HikariConfig coreHikariConfig(){
//    return new HikariConfig();
//  }
//
//  @Bean
//  HikariDataSource coreDataSource(@Qualifier ("coreHikariConfig") HikariConfig config){
//    return new HikariDataSource(config);
//  }
//}
