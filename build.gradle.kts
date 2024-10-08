import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.2.3"
    id("io.spring.dependency-management") version "1.1.4"
    id("com.google.cloud.tools.jib") version "3.4.1"
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.spring") version "1.9.22"
    kotlin("plugin.jpa") version "1.9.22"
    kotlin("plugin.allopen") version "1.9.22"

    kotlin("kapt") version "1.9.22"
}

group = "com.ggsdh"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    implementation("com.linecorp.kotlin-jdsl:jpql-dsl:3.5.1")
    implementation("com.linecorp.kotlin-jdsl:jpql-render:3.5.1")

    // Swagger
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("com.mysql:mysql-connector-j")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")

    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    implementation("io.jsonwebtoken:jjwt-gson:0.11.5")

    implementation("org.hibernate:hibernate-core:6.2.4.Final")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    implementation("org.asynchttpclient:async-http-client:3.0.0")

    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")

    implementation("com.auth0:java-jwt:4.4.0")
}

kapt {
    javacOptions {
        option("querydsl.entityAccessors", true)
    }
    arguments {
        arg("plugin", "com.querydsl.apt.jpa.JPAAnnotationProcessor")
    }
}
allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.processResources {
    dependsOn("initConfig")
}

tasks.register<Copy>("initConfig") {
    from("./CONFIG")
    include("*.yml")
    into("./src/main/resources")
}

jib {
    from {
        image = "amazoncorretto:17-alpine3.18"
    }
    to {
        image = "ggsdh/ggsdh-operate:latest"
        container {
            jvmFlags =
                listOf(
                    "-Dspring.profiles.active=real",
                    "-Dserver.port=8080",
                    "-XX:+UseContainerSupport",
                )

            ports = listOf("8080")
        }
    }
}
