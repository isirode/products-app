package com.isirode.products.deploy.vps.steps.podman

import com.isirode.products.deploy.vps.commands.SftpCommand
import com.isirode.products.deploy.vps.commands.SshExecCommand
import com.isirode.products.deploy.vps.configuration.Configuration
import com.jcraft.jsch.Session
import org.apache.commons.io.IOUtils
import org.apache.logging.log4j.LogManager
import java.io.File

class PodmanPod {

    companion object {
        val logger = LogManager.getLogger(this::class.java.name)
    }

    fun execute(
        session: Session,
        conf: Configuration.PodmanConfiguration,
        apiConf: Configuration.ApiConfiguration, webConf: Configuration.WebConfiguration,
        orientDbPassword: String,
        version: String
    ) {

        logger.info("${this::class.simpleName} of version $version")

        // val apiImageName = "${remoteApiImageName}:${version}"

        // Pull containers
        logger.info("Pulling images")

        logger.info("Pulling image docker.io/library/orientdb:3.2.5")
        // Info : podman might be redirecting output to errstream if image already pulled
        SshExecCommand(false).execute(session, "podman pull docker.io/library/orientdb:3.2.5")
        logger.info("Pulling image ${apiConf.remoteImageName}")
        SshExecCommand(false).execute(session, "podman pull ${apiConf.remoteImageName}:$version")
        logger.info("Pulling image ${webConf.remoteImageName}")
        SshExecCommand(false).execute(session, "podman pull ${webConf.remoteImageName}:$version")

        // get file
        logger.info("Pushing pod file")
        val podFileAsString = File(conf.localPodFileName).readText(Charsets.UTF_8)
        val replacedPodFileAsString = podFileAsString.replace("\${ORIENTDB_ROOT_PASSWORD}", orientDbPassword)

        // send file
        val podFileAsStream = IOUtils.toInputStream(replacedPodFileAsString)
        SftpCommand().putFile(session, podFileAsStream, conf.remotePodFileName)

        // stop pod
        logger.info("Stopping pod ${conf.podName}")
        // TODO : check if exists podman pod exists products-api
        // podman play kube --down demo.yml ?
        SshExecCommand(false).execute(session, "podman pod stop ${conf.podName}")

        // remove pod
        logger.info("Removing pod ${conf.podName}")
        SshExecCommand(false).execute(session, "podman pod rm ${conf.podName}")

        // start pod
        logger.info("Starting pod ${conf.podName} with file ${conf.remotePodFileName}")
        SshExecCommand().execute(session, "podman play kube ${conf.remotePodFileName}")
    }

}