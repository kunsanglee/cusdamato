import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    kotlin("plugin.jpa") version "1.9.25" // @NoArgs 플러그인 포함되어 있음.
// 	kotlin("kapt") version "1.9.25" // kapt 플러그인 추가
    id("org.springframework.boot") version "3.4.1"
    id("io.spring.dependency-management") version "1.1.7"
    // ktlint 플러그인 추가
    id("org.jlleitschuh.gradle.ktlint") version "11.4.0"
}

group = "server"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    runtimeOnly("com.h2database:h2")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // 필요 시 kapt 의존성 추가 (예: Lombok)
    // kapt("org.projectlombok:lombok:1.18.24")
    // compileOnly("org.projectlombok:lombok:1.18.24")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += listOf("-Xjsr305=strict")
        jvmTarget = "21"
    }
}

// kotlin은 기본적으로 모든 클래스와 메서드를 final로 선언하는데,
// JPA와 같은 프레임워크는 프록시 객체를 생성을 위해 클래스와 메서드가 open이어야 한다.
// allOpen 플러그인은 kotlin("plugin.jpa") 플러그인에 포함되어 있으며,
// 기본적으로 @Entity, @MappedSuperclass, @Embeddable 어노테이션이 적용된 클래스들을 open으로 만든다.
// 그러나 추가적인 어노테이션을 지정하고 싶다면, allOpen 블록을 사용하여 설정할 수 있습니다.
allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
