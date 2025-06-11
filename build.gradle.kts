import com.github.davidmc24.gradle.plugin.avro.GenerateAvroJavaTask

plugins {
	java
	id("org.springframework.boot") version "3.5.0"
	id("io.spring.dependency-management") version "1.1.7"
    id("jacoco")
    id("io.freefair.lombok") version "8.13.1"
    id("com.github.davidmc24.gradle.plugin.avro") version "1.9.1"
}

group = "com.scr.project"
version = "0.0.1-SNAPSHOT"
private val apacheAvroVersion: String by project
private val rewardedManagementVersion: String by project

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
    implementation("org.apache.avro:avro:$apacheAvroVersion")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    runtimeOnly("org.postgresql:postgresql")
    add("avroSchemas", "com.scr.project:service-rewarded-management:$rewardedManagementVersion:schemas")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.h2database:h2")
    testImplementation("org.mockito:mockito-core")
    testImplementation("org.mockito:mockito-junit-jupiter")
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

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        html.required.set(true)
        csv.required.set(false)
    }
}

tasks.register("printCoverage") {
    group = "verification"
    description = "Prints the code coverage of the project"
    dependsOn(tasks.jacocoTestReport)
    doLast {
        val reportFile = layout.buildDirectory.file("reports/jacoco/test/jacocoTestReport.xml").get().asFile
        if (reportFile.exists()) {
            val factory = javax.xml.parsers.DocumentBuilderFactory.newInstance()
            factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false)
            val builder = factory.newDocumentBuilder()
            val document = builder.parse(reportFile)
            val counters = document.getElementsByTagName("counter")
            var covered = 0
            var missed = 0
            for (i in 0 until counters.length) {
                val counter = counters.item(i) as org.w3c.dom.Element
                covered += counter.getAttribute("covered").toInt()
                missed += counter.getAttribute("missed").toInt()
            }
            val totalCoverage = (covered * 100.0) / (covered + missed)
            println("Total Code Coverage: %.2f%%".format(totalCoverage))
        } else {
            println("JaCoCo report file not found!")
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    finalizedBy("jacocoTestReport", tasks.named("printCoverage"))
}

