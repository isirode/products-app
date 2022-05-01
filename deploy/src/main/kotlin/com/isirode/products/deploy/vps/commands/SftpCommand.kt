package com.isirode.products.deploy.vps.commands

import com.jcraft.jsch.ChannelSftp
import com.jcraft.jsch.Session
import java.io.InputStream

// TODO : maybe create extension instead
class SftpCommand {

    fun putFile(session: Session, localFileName: String, remoteFileName: String) {
        var channelSftp: ChannelSftp? = null
        try {
            channelSftp = session.openChannel("sftp") as ChannelSftp
            channelSftp.connect()

            channelSftp.put(localFileName, remoteFileName)
        } finally {
            channelSftp?.exit()
        }
    }

    fun putFile(session: Session, localFileContentAsStream: InputStream, remoteFileName: String) {
        var channelSftp: ChannelSftp? = null
        try {
            channelSftp = session.openChannel("sftp") as ChannelSftp
            channelSftp.connect()

            channelSftp.put(localFileContentAsStream, remoteFileName)
        } finally {
            channelSftp?.exit()
        }
    }

}