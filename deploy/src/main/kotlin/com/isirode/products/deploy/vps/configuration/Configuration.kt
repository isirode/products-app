package com.isirode.products.deploy.vps.configuration

import com.typesafe.config.ConfigFactory
import io.github.config4k.extract
import java.io.File

class Configuration(
    val vpsSSHAuth: VpsSSHAuth,
    val dockerRegistryAuth: DockerRegistryAuth,
    val orientDB: OrientDB,
    val steps: Steps
) {

    class VpsSSHAuth(
        val username: String,
        val password: String,
        val address: String
    )

    class DockerRegistryAuth(
        val username: String,
        val password: String
    )

    // TODO : mutualise this with secret-configuration for the Pod
    class OrientDB(val password: String)

    // TODO : mutualise those ?
    class ApiConfiguration(
        val localImageName: String,
        val remoteImageName: String,
        val dockerFileName: String,
        val dockerWorkingDir: String,
        val dataSourceDirectory: String
    )
    class WebConfiguration(
        val localImageName: String,
        val remoteImageName: String,
        val dockerFileName: String
    )
    class ApiSecretConfiguration (
        val localSecretFileName: String,
        val remoteSecretFileName: String
    )
    class PodmanConfiguration(
        val localPodFileName: String,
        val remotePodFileName: String,
        val podName: String,
        val apiSecret: ApiSecretConfiguration
    )
    class NginxConfiguration(
        val localFileName: String,
        val remoteFileName: String
    )
    class Steps(
        val api: ApiConfiguration,
        val web: WebConfiguration,
        val podman: PodmanConfiguration,
        val apiNginx: NginxConfiguration,
        val webNginx: NginxConfiguration
    )

    companion object {
        val CONFIGURATION_NAMESPACE = "com.isirode.products.deploy.vps"

        fun loadConfiguration(secondaryFile: String?): Configuration {
            val applicationConfAsString = this::class.java.classLoader.getResource("application.conf")?.readText(Charsets.UTF_8)
                ?: throw RuntimeException("application.conf must be present")
            val applicationConf = ConfigFactory.parseString(applicationConfAsString.trimMargin())

            if (!secondaryFile.isNullOrEmpty()) {
                // TODO : handle resources files & other types when mutualise
                val priorityConfAsString = this::class.java.classLoader.getResource(secondaryFile)?.readText(Charsets.UTF_8)
                val priorityConf = ConfigFactory.parseString(priorityConfAsString)
                val finalConf = priorityConf.resolveWith(applicationConf)

                return finalConf.extract(CONFIGURATION_NAMESPACE)
            }

            return applicationConf.extract(CONFIGURATION_NAMESPACE)
        }

    }

}