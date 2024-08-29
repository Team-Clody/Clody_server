import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.gradle.internal.impldep.org.junit.experimental.categories.Categories.CategoryFilter.include

plugins {
    java
    id("org.springframework.boot") version "3.3.0"
}

group = "com.clody"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":clody-domain"))
    implementation(project(":clody-infra"))
    implementation("org.springframework.boot:spring-boot-starter-batch")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.batch:spring-batch-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    //Lombok
    annotationProcessor("org.projectlombok:lombok")
    compileOnly("org.projectlombok:lombok")

    runtimeOnly("org.postgresql:postgresql")

}

tasks.withType<Test> {
    useJUnitPlatform()
}

the<DependencyManagementExtension>().apply {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudDependenciesVersion")}")
    }
}
