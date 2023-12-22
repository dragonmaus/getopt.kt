plugins {
    kotlin("jvm") version "1.9.22"
    `java-library`
    `maven-publish`
}

group = "us.dragonma"
version = "1.0.2"

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform(kotlin("bom")))
    implementation(kotlin("stdlib-jdk8"))
    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
}

java {
    withSourcesJar()
}

publishing {
    repositories {
        maven {
            url = uri("${layout.buildDirectory}/repo")
        }
    }
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}
