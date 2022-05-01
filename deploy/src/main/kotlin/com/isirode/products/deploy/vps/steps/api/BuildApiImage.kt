package com.isirode.products.deploy.vps.steps.api

import com.github.dockerjava.api.DockerClient
import com.isirode.products.deploy.vps.configuration.Configuration
import com.isirode.products.deploy.vps.docker.BuildImageCallback
import org.apache.commons.io.FileUtils
import org.apache.commons.io.filefilter.DirectoryFileFilter
import org.apache.commons.io.filefilter.WildcardFileFilter
import org.apache.logging.log4j.LogManager
import java.io.File

class BuildApiImage {

    companion object {
        val logger = LogManager.getLogger(this::class.java.name)
    }

    fun execute(dockerClient: DockerClient, conf: Configuration.ApiConfiguration, version: String): String {

        // TODO : will not work if executed elsewhere
        // TODO : remove magic string from here
        // TODO : tag with latest
        val destDirectory = "api/products-data"
        logger.info("Copying directory ${conf.dataSourceDirectory} to $destDirectory")
        val sourceDirectory: File = File(conf.dataSourceDirectory)
        val destinationDirectory: File = File(destDirectory)
        FileUtils.copyDirectory(sourceDirectory, destinationDirectory, DirectoryFileFilter.DIRECTORY.or(WildcardFileFilter("*.toml")))

        return dockerClient
            .buildImageCmd(File(conf.dockerFileName))
            .withBuildArg("VERSION",version)
            .withBuildArg("SRC_DATA_FOLDER", "products-data")
            .withBuildArg("WORKING_DIR", conf.dockerWorkingDir)
            .withTags(setOf("${conf.localImageName}:${version}", "${conf.remoteImageName}:${version}"))
            .exec(BuildImageCallback())
            .awaitImageId()
    }

}