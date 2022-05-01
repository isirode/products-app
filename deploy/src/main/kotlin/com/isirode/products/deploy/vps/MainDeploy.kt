package com.isirode.products.deploy.vps

import com.github.dockerjava.api.DockerClient
import com.github.dockerjava.api.model.AuthConfig
import com.github.dockerjava.core.DefaultDockerClientConfig
import com.github.dockerjava.core.DockerClientBuilder
import com.github.dockerjava.core.DockerClientConfig
import com.isirode.products.deploy.vps.configuration.Configuration
import com.isirode.products.deploy.vps.steps.*
import com.isirode.products.deploy.vps.steps.api.BuildApiImage
import com.isirode.products.deploy.vps.steps.api.PushApiImage
import com.isirode.products.deploy.vps.steps.podman.PodmanPod
import com.isirode.products.deploy.vps.steps.podman.PodmanSecret
import com.isirode.products.deploy.vps.steps.web.BuildWebImage
import com.isirode.products.deploy.vps.steps.web.PushWebImage
import com.jcraft.jsch.JSch
import com.jcraft.jsch.Session
import org.apache.logging.log4j.LogManager
import kotlin.RuntimeException

fun getSession(username: String, password: String, host: String, port: Int): Session {
    val session = JSch().getSession(username, host, port)
    session.setPassword(password)
    session.setConfig("StrictHostKeyChecking", "no")
    session.connect()
    return session
}

// TODO : use commands
// TODO : put a warning system if shadow was not called
fun main() {

    val logger = LogManager.getLogger({}.javaClass.enclosingMethod.name)

    val executionDir = System.getProperty("user.dir") ?: throw RuntimeException("user.dir is not set")
    if (!executionDir.endsWith("products-app")) {
        throw RuntimeException("Executable in root-dir only for now, your execution directory is $executionDir")
    }

    // TODO : make it a parameter when passing to kotlinx-cli
    val configuration = Configuration.loadConfiguration("local.conf")

    var session: Session? = null

    try {
        val currentVersion = "0.0.1"
        val mustBuildApiImage = true
        val mustPushApiImage = true
        val mustBuildWebImage = true
        val mustPushWebImage = true

        session = getSession(
            configuration.vpsSSHAuth.username,
            configuration.vpsSSHAuth.password,
            configuration.vpsSSHAuth.address,
            22
        )

        // FIXME : maybe build the shadowJar here

        val dockerConfig: DockerClientConfig = DefaultDockerClientConfig.createDefaultConfigBuilder()
            .withDockerHost("tcp://localhost:2375")
            /*
            .withDockerTlsVerify(true)
            .withDockerCertPath("/home/user/.docker")
            .withRegistryUsername(registryUser)
            .withRegistryPassword(registryPass)
            .withRegistryEmail(registryMail)
            .withRegistryUrl(registryUrl)
            */
            .build()

        val dockerAuth = AuthConfig()
            .withUsername(configuration.dockerRegistryAuth.username)
            .withPassword(configuration.dockerRegistryAuth.password)

        val dockerClient: DockerClient = DockerClientBuilder
            .getInstance(dockerConfig)
            .build()

        // API
        if (mustBuildApiImage) {
            BuildApiImage().execute(dockerClient, configuration.steps.api, currentVersion)
        }
        if (mustPushApiImage) {
            PushApiImage().execute(
                dockerClient,
                dockerAuth,
                configuration.steps.api,
                currentVersion
            )
        }
        // Web
        if (mustBuildWebImage) {
            BuildWebImage().execute(dockerClient, configuration.steps.web, currentVersion)
        }
        if (mustPushWebImage) {
            PushWebImage().execute(dockerClient, dockerAuth, configuration.steps.web, currentVersion)
        }

        // Secret
        PodmanSecret().execute(session, configuration.steps.podman)

        // Pod
        // TODO : execute not clear, add step to the class or rename the method
        PodmanPod(
            // BuildApiImage.vpsImagePrefix,
        ).execute(
            session,
            configuration.steps.podman,
            configuration.steps.api,
            configuration.steps.web,
            configuration.orientDB.password,
            currentVersion
        )

        // Nginx
        // Info : still need to restart Nginx by hand for now
        NginxStep().execute(session, configuration.steps.apiNginx, configuration.steps.webNginx)

        // TODO : check logs of podman
        // TODO : check if api is available

    } finally {
        session?.disconnect()
    }
}