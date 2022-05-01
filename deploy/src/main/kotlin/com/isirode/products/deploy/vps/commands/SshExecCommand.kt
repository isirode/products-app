package com.isirode.products.deploy.vps.commands

import com.jcraft.jsch.ChannelExec
import com.jcraft.jsch.Session
import org.apache.logging.log4j.LogManager
import java.io.ByteArrayOutputStream

class SshExecCommand(val throwOnError: Boolean = true) {

    companion object {
        val logger = LogManager.getLogger(this::class.java.name)
    }

    fun execute(session: Session, command: String) {
        var channel: ChannelExec? = null
        try {
            channel = session.openChannel("exec") as ChannelExec
            channel.setCommand(command)

            val responseStream = ByteArrayOutputStream()
            val errorStream = ByteArrayOutputStream()
            channel.outputStream = responseStream
            channel.setErrStream(errorStream)
            channel.connect()

            // TODO : replace by a Kotlin method
            while (channel.isConnected) {
                Thread.sleep(100)
            }

            val responseString: String = String(responseStream.toByteArray())
            println(responseString)

            val errorString: String = String(errorStream.toByteArray())
            if (!errorString.isEmpty()) {
                logger.error(errorString)
                if (throwOnError) {
                    throw RuntimeException(errorString)
                }
            }
        } finally {
            channel?.disconnect()
        }
    }

}