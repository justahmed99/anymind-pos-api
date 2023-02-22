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
}

//apply{
//	from("$projectDir/grpc.gradle.kts")
//}

group = "com.anymind"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation("net.devh:grpc-server-spring-boot-starter:2.13.1.RELEASE")

	implementation("io.grpc:grpc-protobuf:${grpcVersion}")
	implementation("io.grpc:grpc-stub:1.40.1")
	implementation("io.grpc:grpc-kotlin-stub:1.1.0")
	implementation("com.google.protobuf:protobuf-java:$protobufVersion")
	implementation("com.google.protobuf:protobuf-kotlin:$protobufVersion")

	compileOnly("jakarta.annotation:jakarta.annotation-api:1.3.5") // Java 9+ compatibility - Do NOT update to 2.0.0

	implementation("net.devh:grpc-client-spring-boot-starter:2.13.1.RELEASE")

	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")



//	implementation("io.github.lognet:grpc-spring-boot-starter:3.4.3")
//	compileOnly("org.projectlombok:lombok:1.18.12")
//	annotationProcessor("org.projectlombok:lombok:1.18.12")
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
				kotlin {
//					option("kotlin_package=proto.generated")
//					option("jvm_opt=--kotlin.generated.classes=proto.generated")
				}
			}

			task.plugins {
				id("kotlin")
				id("grpc")
				id("grpckt")
			}
		}
	}
}

tasks.named("bootRun").configure {
	dependsOn("generateProto")
}