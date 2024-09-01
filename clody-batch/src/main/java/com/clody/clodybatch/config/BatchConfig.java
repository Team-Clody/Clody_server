package com.clody.clodybatch.config;

import com.zaxxer.hikari.HikariDataSource;
import java.util.Collection;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.batch.BatchDataSource;
import org.springframework.boot.autoconfigure.batch.BatchDataSourceScriptDatabaseInitializer;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.boot.autoconfigure.batch.JobLauncherApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.StringUtils;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(BatchProperties.class)
public class BatchConfig {

  public static final String BATCH_DATASOURCE = "batchDataSource";
  public static final String BATCH_TRANSACTION_MANAGER = "batchTransactionManager";

  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnProperty(prefix = "spring.batch.job", name = "enabled", havingValue = "true", matchIfMissing = true)
  public JobLauncherApplicationRunner jobLauncherApplicationRunner(JobLauncher jobLauncher, JobExplorer jobExplorer,
      JobRepository jobRepository, BatchProperties properties, Collection<Job> jobs) {
    JobLauncherApplicationRunner runner = new JobLauncherApplicationRunner(jobLauncher, jobExplorer, jobRepository);
    String jobNames = properties.getJob().getName();
    if (StringUtils.hasText(jobNames)) {
      if (jobs.stream().map(Job::getName).noneMatch(s -> s.equals(jobNames))){
        throw new IllegalArgumentException(jobNames + "는 등록되지 않은 job name입니다. job name을 확인하세요.");
      }
      runner.setJobName(jobNames);
    }
    return runner;
  }

  @Bean
  @ConditionalOnMissingBean(BatchDataSourceScriptDatabaseInitializer.class)
  BatchDataSourceScriptDatabaseInitializer batchDataSourceInitializer(DataSource dataSource,
      @BatchDataSource ObjectProvider<DataSource> batchDataSource, BatchProperties properties) {
    return new BatchDataSourceScriptDatabaseInitializer(
        batchDataSource.getIfAvailable(() -> dataSource),
        properties.getJdbc());
  }

  @Primary
  @BatchDataSource
  @Bean(BATCH_DATASOURCE)
  @ConfigurationProperties(prefix = "spring.datasource.batch.hikari")
  public DataSource batchDataSource() {
    return DataSourceBuilder.create().type(HikariDataSource.class).build();
  }

  @Bean(BATCH_TRANSACTION_MANAGER)
  public PlatformTransactionManager batchTransactionManager(
      @Qualifier(BATCH_DATASOURCE) DataSource batchDataSource) {
    return new DataSourceTransactionManager(batchDataSource);
  }
}
