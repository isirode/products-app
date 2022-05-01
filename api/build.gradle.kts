import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

// TODO : common between apps, move it
val orientDBVersion = "3.2.5"

plugins {
    id("products.java.kotlin-application-conventions")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

application {
    // TODO : try to use reflection here
    mainClass.set("com.isirode.products.api.ApiMainKt")
}

// FIXME : version kotlin
dependencies {
    implementation(project(":schema"))
    // Ktor
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-jackson:$ktor_version")
    implementation("io.ktor:ktor-auth:$ktor_version")
    // Info : Jetty will not work at the uber jar phase, use only Netty (ktor_version: 1.6.7)
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    // Logging
    // implementation("ch.qos.logback:logback-classic:$logback_version")
    // Logging
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.17.2")
    implementation("org.apache.logging.log4j:log4j-core:2.17.2")
    implementation("org.apache.logging.log4j:log4j-api:2.17.2")
    // OrientDB
    implementation("com.orientechnologies:orientdb-client:$orientDBVersion")
    // Config HOCON
    implementation("io.github.config4k:config4k:0.4.2")// TODO : put in common convention
    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}
repositories {
    mavenCentral()
}

// FIXME : do not seem useful but the documentation indicate it
tasks {
    shadowJar {
        manifest {
            attributes(Pair("Main-Class", "com.onesime.products.ApiMainKt"))
        }
    }
}