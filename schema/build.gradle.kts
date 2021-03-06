/*
 * This file was generated by the Gradle 'init' task.
 */

plugins {
    id("products.java.kotlin-application-conventions")
    id("io.freefair.lombok") version "6.4.1"
}

val orientDBVersion = "3.2.5"
val jacksonVersion = "2.13.1"

dependencies {
    implementation("org.apache.commons:commons-text")// FIXME : version
    implementation("com.orientechnologies:orientdb-client:$orientDBVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-toml:$jacksonVersion")
    implementation("com.kjetland:mbknor-jackson-jsonschema_2.13:1.0.39")
    implementation("org.jetbrains.kotlinx:kotlinx-cli:0.3.4")
    // implementation(project(":utilities"))
    implementation(platform("org.testcontainers:testcontainers-bom:1.16.3"))
    testImplementation("org.testcontainers:orientdb")
    testImplementation("org.assertj:assertj-core:3.21.0")
}

application {
    // Define the main class for the application.
    mainClass.set("products.java.app.AppKt")
}
