import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    application
    kotlin("plugin.jpa") version "1.6.10"
    id("org.springframework.boot") version "2.4.4"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("plugin.spring") version "1.6.10"
}

group = "me.jmvsta.gameredactor"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux:2.6.7")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions:1.1.6")
    runtimeOnly("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.6.2")
    implementation("org.springframework.boot:spring-boot-configuration-processor:2.6.7")
    implementation("com.ninja-squad:springmockk:3.1.1")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive:2.7.1")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
        exclude(group = "org.mockito", module = "mockito-core")
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.register("stage") {
    dependsOn("build")
}

application {
    mainClass.set("me.jmvsta.GameRedactorKt")
}