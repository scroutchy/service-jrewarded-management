import com.github.davidmc24.gradle.plugin.avro.GenerateAvroJavaTask

plugins {
	java
	id("org.springframework.boot") version "3.5.0"
	id("io.spring.dependency-management") version "1.1.7"
    id("io.freefair.lombok") version "8.13.1"
    id("com.github.davidmc24.gradle.plugin.avro") version "1.9.1"
}

group = "com.scr.project"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
    maven("https://gitlab.com/api/v4/projects/69406479/packages/maven")
    maven("https://packages.confluent.io/maven/")
}

configurations {
    create("avroSchemas")
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.kafka:spring-kafka")
    implementation("org.projectlombok:lombok")
    implementation("org.apache.avro:avro:1.12.0")
    implementation("io.projectreactor.kafka:reactor-kafka:1.3.23")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    add("avroSchemas", "com.scr.project:service-rewarded-management:0.1.2:schemas")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.h2database:h2")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
val extractAvroSchemas by tasks.registering(Copy::class) {
    from(configurations["avroSchemas"].map { zipTree(it) })
    include("**/*.avsc")
    into(layout.buildDirectory.dir("schemas-libs"))
}

tasks.named<GenerateAvroJavaTask>("generateAvroJava") {
    dependsOn(extractAvroSchemas)
    source(layout.buildDirectory.dir("schemas-libs"))
}

