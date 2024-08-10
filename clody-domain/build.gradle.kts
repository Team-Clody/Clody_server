import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot") version "3.3.0"
    java
}

dependencies {
    //의존성 끊어야 됨
    implementation(project(":clody-support"))
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    //Lombok
    annotationProcessor("org.projectlombok:lombok")
    compileOnly("org.projectlombok:lombok")

    //profanityFilter
    implementation("io.github.vaneproject:badwordfiltering:1.0.0")

}

tasks.withType<BootJar> {
    enabled = false
}
tasks.withType<Jar> {
    enabled = true
}
tasks.withType<Test> {
    useJUnitPlatform()
}

