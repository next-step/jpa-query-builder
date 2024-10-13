plugins {
    kotlin("jvm") version "2.0.20"
}

group = "camp.nextstep.edu"
version = "1.0-SNAPSHOT"

kotlin {
    jvmToolchain(21)
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
    implementation("ch.qos.logback:logback-classic:1.4.12")
    implementation("com.h2database:h2:2.2.220")
    implementation("org.reflections:reflections:0.10.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.3")
    testImplementation("org.assertj:assertj-core:3.24.2")
}

tasks.test {
    useJUnitPlatform()
}
