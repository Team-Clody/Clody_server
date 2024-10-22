import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot") version "3.3.0"
    java
}
dependencies {
    implementation(project(":clody-domain"))
    implementation(project(":clody-support"))
//    implementation(project(":clody-app"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    runtimeOnly("org.postgresql:postgresql")

    testImplementation("org.springframework.boot:spring-boot-starter-test")

    //Feign
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")

    //FCM
    implementation("com.google.firebase:firebase-admin:9.3.0")

    annotationProcessor("org.projectlombok:lombok")
    compileOnly("org.projectlombok:lombok")

    // ObjectMapper
    implementation("com.fasterxml.jackson.core:jackson-databind:2.16.1")

    //Client 생성
    implementation("com.nimbusds:nimbus-jose-jwt:3.10")

    //JWT
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
    implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")

    //AWS
    implementation(platform("io.awspring.cloud:spring-cloud-aws-dependencies:3.0.1"))
    implementation("io.awspring.cloud:spring-cloud-aws-starter-sqs")

    //azure
    implementation("com.azure:azure-ai-openai-assistants:1.0.0-beta.3")

    //scheduling
    implementation("org.springframework.boot:spring-boot-starter-quartz:3.2.3")


}

tasks.withType<BootJar>{
    enabled = false
}
tasks.withType<Jar>{
    enabled = true
}
tasks.withType<Test> {
    useJUnitPlatform()
}
the<DependencyManagementExtension>().apply {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudDependenciesVersion")}")
    }
}
