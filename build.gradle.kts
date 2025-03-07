plugins {
    java
    id("org.springframework.boot") version "3.4.3"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.kangwon"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
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

subprojects {
    apply(plugin = "java")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.springframework.boot")

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")

        compileOnly("org.projectlombok:lombok")
        annotationProcessor("org.projectlombok:lombok")
        runtimeOnly("com.h2database:h2")
        runtimeOnly("com.mysql:mysql-connector-java")
        implementation("org.springframework.boot:spring-boot-starter")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
