plugins {
    id("java")
}

group = "ovh.milosztura"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    implementation("ai.koog:koog-agents:1.2.0")

    implementation("ai.koog:koog-agents-additions:1.2.0-beta")

}

tasks.test {
    useJUnitPlatform()
}