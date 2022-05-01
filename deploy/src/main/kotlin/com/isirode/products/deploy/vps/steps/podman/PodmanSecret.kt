package com.isirode.products.deploy.vps.steps.podman

import com.isirode.products.deploy.vps.commands.SftpCommand
import com.isirode.products.deploy.vps.configuration.Configuration
import com.jcraft.jsch.Session
import org.apache.logging.log4j.LogManager

// VPS is still using podman 3.0.x, no secrets yet
// would be in "/run/secrets/configuration" otherwise
class PodmanSecret {

    // TODO : check validity of the secret conf

    companion object {
        val logger = LogManager.getLogger(this::class.java.name)
    }

    fun execute(session: Session, conf: Configuration.PodmanConfiguration) {

        logger.info("${this::class.simpleName} remoteSecretFileName")

        // push
        SftpCommand().putFile(session, conf.apiSecret.localSecretFileName, conf.apiSecret.remoteSecretFileName)

        // create
        // SshExecCommand().execute(session, "podman secret create ${secretName} ${remoteSecretFileName}")

        // remove file
        // SshExecCommand().execute(session, "rm ${remoteSecretFileName}")
    }

}