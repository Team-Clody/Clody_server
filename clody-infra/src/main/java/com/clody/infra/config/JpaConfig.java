package com.clody.infra.config;

import com.clody.domain.DomainBasePackage;
import com.clody.infra.JpaBasePackage;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackageClasses = {JpaBasePackage.class, DomainBasePackage.class})
@EntityScan(basePackageClasses = {JpaBasePackage.class, DomainBasePackage.class})
@EnableJpaRepositories(basePackageClasses = {JpaBasePackage.class, DomainBasePackage.class})
public class JpaConfig {
}
