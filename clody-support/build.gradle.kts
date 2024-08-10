import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot") version "3.3.0"
    java
}

dependencies {

    //lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    //SECURITY
    implementation("org.springframework.boot:spring-boot-starter-security")

    //Jakarta
    compileOnly("jakarta.servlet:jakarta.servlet-api:6.0.0")

    testImplementation("io.rest-assured:spring-mock-mvc:5.4.0")
    testImplementation("io.rest-assured:json-path:5.4.0")
    testImplementation("io.rest-assured:xml-path:5.4.0")
    // Monitoring
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("io.micrometer:micrometer-registry-prometheus")

    // Notification
    implementation("com.google.firebase:firebase-admin:9.3.0")

    //JWT
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
    implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")

    //client secret생성
    implementation("com.nimbusds:nimbus-jose-jwt:3.10")

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



