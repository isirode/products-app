package com.isirode.products.deploy.vps.steps.web

import com.github.dockerjava.api.DockerClient
import com.isirode.products.deploy.vps.configuration.Configuration
import com.isirode.products.deploy.vps.docker.BuildImageCallback
import org.apache.logging.log4j.LogManager
import java.io.File

class BuildWebImage {

    companion object {
        val logger = LogManager.getLogger(this::class.java.name)
    }

    fun execute(dockerClient: DockerClient, conf: Configuration.WebConfiguration, version: String): String {
        return dockerClient
            .buildImageCmd(File(conf.dockerFileName))
            .withTags(setOf("${conf.localImageName}:${version}", "${conf.remoteImageName}:${version}"))
            .exec(BuildImageCallback())
            .awaitImageId()
    }
}