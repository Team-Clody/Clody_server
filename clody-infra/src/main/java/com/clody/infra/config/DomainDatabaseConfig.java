package com.clody.infra.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "com.clody.domain")
@EnableJpaRepositories(
    basePackages = "com.clody.infra.models",
    entityManagerFactoryRef = "domainEntityManagerFactory",
    transactionManagerRef = "domainTransactionManager"
)
public class DomainDatabaseConfig {

}
