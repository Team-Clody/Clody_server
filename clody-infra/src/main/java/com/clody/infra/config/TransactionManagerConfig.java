package com.clody.infra.config;

import static com.clody.infra.config.DataSourceConfig.DOMAIN_DATASOURCE;
import static com.clody.infra.config.DataSourceConfig.META_DATASOURCE;

import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties({JpaProperties.class, HibernateProperties.class})
public class TransactionManagerConfig {

  public static final String DOMAIN_ENTITY_MANAGER_FACTORY = "domainEntityManagerFactory";
  public static final String DOMAIN_TRANSACTION_MANAGER = "domainTransactionManager";
  public static final String META_ENTITY_MANAGER_FACTORY = "metaEntityManagerFactory";
  public static final String META_TRANSACTION_MANAGER = "metaTransactionManager";

  private final JpaProperties jpaProperties;
  private final HibernateProperties hibernateProperties;
  private final EntityManagerFactoryBuilder entityManagerFactoryBuilder;

  // Meta Database Transaction Manager 설정
  @Bean(name = META_TRANSACTION_MANAGER)
  public PlatformTransactionManager metaTransactionManager(
      @Qualifier(META_ENTITY_MANAGER_FACTORY) EntityManagerFactory metaEntityManagerFactory) {
    return new JpaTransactionManager(metaEntityManagerFactory);
  }

  @Bean(name = META_ENTITY_MANAGER_FACTORY)
  public LocalContainerEntityManagerFactoryBean metaEntityManagerFactory(
      @Qualifier(META_DATASOURCE) DataSource metaDataSource) {
    return entityManagerFactoryBuilder
        .dataSource(metaDataSource)
        .packages("com.clody.meta") // Meta 데이터베이스에서 사용하는 엔티티 패키지 지정
        .persistenceUnit("meta")
        .properties(hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings()))
        .build();
  }

  // Domain Database Transaction Manager 설정
  @Primary
  @Bean(name = DOMAIN_TRANSACTION_MANAGER)
  public PlatformTransactionManager domainTransactionManager(
      @Qualifier(DOMAIN_ENTITY_MANAGER_FACTORY) EntityManagerFactory domainEntityManagerFactory) {
    return new JpaTransactionManager(domainEntityManagerFactory);
  }

  @Primary
  @Bean(name = DOMAIN_ENTITY_MANAGER_FACTORY)
  public LocalContainerEntityManagerFactoryBean domainEntityManagerFactory(
      @Qualifier(DOMAIN_DATASOURCE) DataSource domainDataSource) {
    return entityManagerFactoryBuilder
        .dataSource(domainDataSource)
        .packages("com.clody.domain")
        .persistenceUnit("domain")
        .properties(hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings()))
        .build();
  }
}
