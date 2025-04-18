plugins {
    id("org.springframework.boot") version "3.2.5" apply false
    id("io.spring.dependency-management") version "1.1.4" apply false
    kotlin("jvm") version "1.9.23" apply false
    kotlin("plugin.spring") version "1.9.23" apply false
    kotlin("kapt") version "1.9.23" apply false
    kotlin("plugin.jpa") version "1.9.23" apply false
}

allprojects {
    group = "com.example"
    version = "1.0.0"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "io.spring.dependency-management")

    // 의존성 추가 (안정적으로 작동)
    dependencies {
        add("implementation", "org.jetbrains.kotlin:kotlin-reflect:1.9.23")
        add("implementation", "org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.9.23")
        add("testImplementation", "org.jetbrains.kotlin:kotlin-test:1.9.23")

        add("testImplementation", "org.springframework.boot:spring-boot-starter-test") {
            exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
        }
    }

    // BOM (Spring Cloud)
    extensions.configure<io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension> {
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:2023.0.1")
        }
    }

    // Java/Kotlin 설정
    plugins.withId("org.jetbrains.kotlin.jvm") {
        extensions.configure<org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension> {
            jvmToolchain(17)
        }
    }

    // JUnit 5 설정
    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
