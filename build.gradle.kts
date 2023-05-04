group = "io.mwi"
version = "0.0.1-SNAPSHOT"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.1.0")
    implementation("org.keycloak:keycloak-admin-client:21.1.1")
    implementation("com.github.ben-manes.caffeine:caffeine")
    compileOnly("org.mapstruct:mapstruct:1.5.5.Final")
    compileOnly("org.projectlombok:lombok:1.18.26")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.mapstruct:mapstruct:1.5.5.Final")
    testImplementation("org.testcontainers:testcontainers:1.18.0")
    testImplementation("org.testcontainers:junit-jupiter:1.18.0")
    testImplementation("com.github.dasniko:testcontainers-keycloak:2.5.0")
    testCompileOnly("org.projectlombok:lombok:1.18.26")


    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")
    annotationProcessor("org.projectlombok:lombok:1.18.26")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")

    testAnnotationProcessor("org.projectlombok:lombok:1.18.26")
}

plugins {
    java
    id("org.springframework.boot") version "3.0.6"
    id("io.spring.dependency-management") version "1.1.0"
}
java.sourceCompatibility = JavaVersion.VERSION_19
java.targetCompatibility = JavaVersion.VERSION_19

repositories {
    mavenCentral()
}

tasks.withType<Test> {
    useJUnitPlatform()
}
