pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
    val springBootVersion: String by settings
    val springDependencyManagementVersion: String by settings
    val swaggerGeneratorVersion: String by settings

    plugins {
        id("org.springframework.boot") version springBootVersion
        id("io.spring.dependency-management") version springDependencyManagementVersion
        id("org.hidetake.swagger.generator") version swaggerGeneratorVersion
    }
}

rootProject.name = "server"

include(":clody-support")
include(":clody-domain")
include(":clody-infra")
include(":clody-app")
include(":clody-batch")

