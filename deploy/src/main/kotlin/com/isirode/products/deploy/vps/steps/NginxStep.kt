package com.isirode.products.deploy.vps.steps

import com.isirode.products.deploy.vps.commands.SftpCommand
import com.isirode.products.deploy.vps.configuration.Configuration
import com.jcraft.jsch.Session
import org.apache.logging.log4j.LogManager

class NginxStep {

    companion object {
        val logger = LogManager.getLogger(this::class.java.name)
    }

    fun execute(session: Session, apiNginxConf: Configuration.NginxConfiguration, webNginxConf: Configuration.NginxConfiguration) {

        // transfer file
        // API
        logger.info("Transfering file ${apiNginxConf.localFileName} to ${apiNginxConf.remoteFileName}")
        SftpCommand().putFile(session, apiNginxConf.localFileName, apiNginxConf.remoteFileName)
        // Web
        logger.info("Transfering file ${webNginxConf.localFileName} to ${webNginxConf.remoteFileName}")
        SftpCommand().putFile(session, webNginxConf.localFileName, webNginxConf.remoteFileName)

        // TODO : check validity in a unit test Testcontainers
        // Need root access
        // check validity of the conf
        // would be /usr/sbin/nginx -t I think
        // SshExecCommand().execute(session, "")

        // Need root access
        // reload nginx
        // would be /usr/sbin/nginx -s reload I think
        // SshExecCommand().execute(session, "")

    }
}