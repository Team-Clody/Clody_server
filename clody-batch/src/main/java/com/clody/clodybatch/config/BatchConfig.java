//package com.clody.clodybatch.config;
//
//import javax.sql.DataSource;
//import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class BatchConfig extends DefaultBatchConfiguration {
//
//  private final DataSource batchDataSource;
//
//  public BatchConfig(@Qualifier("batchDataSource") DataSource batchDataSource) {
//    this.batchDataSource = batchDataSource;
//  }
//
//  @Override
//  public void setDataSource(DataSource dataSource) {
//    // BatchConfig에서 사용할 DataSource 설정
//    super.setDataSource(batchDataSource);
//  }
//
//  // 비즈니스 로직에서 사용할 DataSource Bean 정의
//  @Bean
//  @Qualifier("businessDataSource")
//  public DataSource businessDataSource() {
//    return DataSourceBuilder.create()
//        .url("jdbc:postgresql://localhost:5432/businessdb")
//        .username("businessuser")
//        .password("businesspassword")
//        .driverClassName("org.postgresql.Driver")
//        .build();
//  }
//
//  // 배치 전용 DataSource Bean 정의
//  @Bean
//  @Qualifier("batchDataSource")
//  public DataSource batchDataSource() {
//    return DataSourceBuilder.create()
//        .url("jdbc:postgresql://localhost:5432/batchdb")
//        .username("batchuser")
//        .password("batchpassword")
//        .driverClassName("org.postgresql.Driver")
//        .build();
//  }
//}
