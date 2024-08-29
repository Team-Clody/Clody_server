package com.clody.infra.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackageClasses = {DomainDatabaseConfig.class})
@EnableJpaRepositories(
    basePackages = "com.clody.infra.meta",
    entityManagerFactoryRef = "metaEntityManagerFactory",
    transactionManagerRef = "metaTransactionManager"
)
public class MetaDatabaseConfig {

}
