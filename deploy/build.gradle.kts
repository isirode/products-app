plugins {
    id("products.java.kotlin-application-conventions")
}

dependencies {
    implementation("com.jcraft:jsch:0.1.55")// ssh client
    implementation("com.github.docker-java:docker-java:3.2.13")// for docker
    implementation("org.apache.commons:commons-exec:1.3")// to execute commands
    implementation("commons-io:commons-io:2.11.0")// to copy dirs
    // Logging
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.17.2")
    implementation("org.apache.logging.log4j:log4j-core:2.17.2")
    implementation("org.apache.logging.log4j:log4j-api:2.17.2")
    // Configuration
    implementation("io.github.config4k:config4k:0.4.2")
}

tasks {
    jar {
        // Info : this allow to exclude the file local.conf located in src/main/resources
        // You might need to exclude it from shadowJar also if you add it
        exclude( "local.conf" )
    }
}