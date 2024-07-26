import org.springframework.boot.gradle.tasks.bundling.BootJar

buildscript {
    extra["restdocsApiSpecVersion"] = "0.17.1"
}

val springCloudVersion = "2023.0.2"

plugins {
    java
    id("org.springframework.boot") version "3.3.1"
    id("io.spring.dependency-management") version "1.1.5"
    id("org.hidetake.swagger.generator") version "2.18.2"
}

group = "com.donkeys_today"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    compileOnly("org.projectlombok:lombok")
    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    //SECURITY
    implementation("org.springframework.boot:spring-boot-starter-security")

    //JWT
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
    implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")

    // Redis
    implementation("org.springframework.boot:spring-boot-starter-data-redis:3.2.1")

    // Client Secret 생성 라이브러리
    implementation("com.nimbusds:nimbus-jose-jwt:3.10")

    // API
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")

    testImplementation("io.rest-assured:spring-mock-mvc:5.4.0")
    testImplementation("io.rest-assured:json-path:5.4.0")
    testImplementation("io.rest-assured:xml-path:5.4.0")

    // Monitoring
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("io.micrometer:micrometer-registry-prometheus")

    // Notification
    implementation("com.google.firebase:firebase-admin:9.3.0")

    // Profanity Filter
    implementation("io.github.vaneproject:badwordfiltering:1.0.0")
}

tasks.test {
    useJUnitPlatform()
}

// Jar 로 빌드
tasks.withType<BootJar> {
}

// Feign
dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
    }
}


