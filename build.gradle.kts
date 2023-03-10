import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.google.protobuf.gradle.*

val protobufVersion by extra("3.18.1")
val protobufPluginVersion by extra("0.8.14")
val grpcVersion by extra("1.43.2")

plugins {
    id("org.springframework.boot") version "2.7.8"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    id("com.google.protobuf") version "0.8.17"
    id("org.flywaydb.flyway") version "7.8.2"
    jacoco
}

group = "com.anymind"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    // Basic Spring Boot and another dependencies
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.postgresql:postgresql:42.5.4")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // gRPC dependencies
    implementation("net.devh:grpc-server-spring-boot-starter:2.13.1.RELEASE")
    implementation("net.devh:grpc-client-spring-boot-starter:2.13.1.RELEASE")
    implementation("io.grpc:grpc-protobuf:${grpcVersion}")
    implementation("io.grpc:grpc-stub:1.40.1")
    implementation("io.grpc:grpc-kotlin-stub:1.1.0")
    implementation("com.google.protobuf:protobuf-java:$protobufVersion")
    implementation("com.google.protobuf:protobuf-kotlin:$protobufVersion")
    compileOnly("jakarta.annotation:jakarta.annotation-api:1.3.5") // Java 9+ compatibility - Do NOT update to 2.0.0
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging.showStandardStreams = true
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:${protobufVersion}"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:${grpcVersion}"
        }
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:1.1.0:jdk7@jar"
        }
    }

    generateProtoTasks {
        ofSourceSet("main").forEach { task ->
            task.builtins {
                java {}
                kotlin {}
            }

            task.plugins {
                id("kotlin")
                id("grpc")
                id("grpckt")
            }
        }
    }
}

tasks.named("build").configure {
    dependsOn("generateProto")
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
}
tasks.jacocoTestReport {
    dependsOn(tasks.test) // tests are required to run before generating the report
}

jacoco {
    toolVersion = "0.8.8"
    reportsDirectory.set(layout.buildDirectory.dir("customJacocoReportDir"))
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(false)
        csv.required.set(false)
        html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
    }
}

flyway {
    url = "jdbc:postgresql://localhost:5432/pos_app"
    user = "postgres"
    password = "development"
    locations = arrayOf("filesystem:src/main/resources/db/migration")
}