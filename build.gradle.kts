import org.springframework.boot.gradle.tasks.bundling.BootJar

buildscript {
    extra["restdocsApiSpecVersion"] = "0.17.1"
}

val springCloudVersion = "2023.0.2"

plugins {
    java
    id("java-library")
    id("org.springframework.boot") apply false
    id("io.spring.dependency-management") apply true
    id("org.hidetake.swagger.generator") apply false
}

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
}

//tasks.test {
//    useJUnitPlatform()
//}

allprojects {
    group = property("projectGroup") as String
    version = property("javaVersion") as String

    repositories {
        mavenCentral()
    }
}

subprojects {

    apply(plugin = "java")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.springframework.boot")

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter")
        testImplementation("org.springframework.boot:spring-boot-starter-test")

    }

    tasks.withType<BootJar> {
        enabled = false;
    }
    tasks.withType<Jar> {
        enabled = true;
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(21))
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}


