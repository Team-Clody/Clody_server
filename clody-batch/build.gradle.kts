import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.gradle.internal.impldep.org.junit.experimental.categories.Categories.CategoryFilter.include
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    java
    id("org.springframework.boot") version "3.3.0"
//    id("io.spring.dependency-management") version "1.1.5"

}

group = "com.clody-batch"
version = "1.0.0"

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
//    implementation(project(":clody-infra"))
//    implementation(project(":clody-support"))
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

tasks.named<BootJar>("bootJar") {
    enabled = true
}

tasks.named<Jar>("jar") {
    enabled = true
}

the<DependencyManagementExtension>().apply {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudDependenciesVersion")}")
    }
}
