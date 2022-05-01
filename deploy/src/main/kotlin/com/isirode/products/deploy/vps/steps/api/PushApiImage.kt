package com.isirode.products.deploy.vps.steps.api

import com.github.dockerjava.api.DockerClient
import com.github.dockerjava.api.async.ResultCallback
import com.github.dockerjava.api.model.AuthConfig
import com.github.dockerjava.api.model.PushResponseItem
import com.isirode.products.deploy.vps.configuration.Configuration
import com.isirode.products.deploy.vps.docker.PushImageCallback
import org.apache.logging.log4j.LogManager
import java.util.concurrent.TimeUnit

class PushApiImage {

    companion object {
        val logger = LogManager.getLogger(this::class.java.name)
    }

    fun execute(dockerClient: DockerClient, authConfig: AuthConfig, conf: Configuration.ApiConfiguration, version: String) {

        logger.info("${this::class.simpleName} of version $version")

        val pushImageResultCallback = PushImageCallback()
        val cmd = dockerClient.pushImageCmd(conf.remoteImageName)
            .withTag(version)
            .withAuthConfig(authConfig)

        cmd.exec<ResultCallback<PushResponseItem>>(pushImageResultCallback)

        pushImageResultCallback.awaitCompletion(90, TimeUnit.SECONDS)
    }

}