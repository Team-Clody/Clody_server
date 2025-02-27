import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    java
    id("org.springframework.boot") version "3.3.0"
    id("org.hidetake.swagger.generator") apply true
}
sourceSets {
    main {
        java {
            exclude("com/clody/clodyapi/legacy")
        }
    }
}

tasks.withType<BootJar> {
    enabled = true;
}

dependencies{
    implementation(project(":clody-domain"))
    implementation(project(":clody-support"))
    implementation(project(":clody-infra")) // 클린 아키텍처를 따르지 않는 간단한 해결책

    annotationProcessor("org.projectlombok:lombok")
    compileOnly("org.projectlombok:lombok")

    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    //Security
    implementation("org.springframework.boot:spring-boot-starter-security")

    // API
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")

    // Profanity Filter
    implementation("io.github.vaneproject:badwordfiltering:1.0.0")

    //Feign
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")

    // Webflux(Mono Access - webhook)
    implementation("org.springframework.boot:spring-boot-starter-webflux")

}

the<DependencyManagementExtension>().apply {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudDependenciesVersion")}")
    }
}
