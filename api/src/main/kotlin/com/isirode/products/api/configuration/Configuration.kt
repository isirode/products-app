package com.isirode.products.api.configuration

import com.typesafe.config.ConfigFactory
import io.github.config4k.extract
import java.io.File

// TODO : multi-level classes
class Configuration(
    val apiPort: Int,
    val orientDbHostName: String,
    val orientDbPassword: String,
    val readerUsername: String,
    val readerPassword: String,
    val databaseName: String,
    val dataFolderPath: String,
    val mustGenerateSchema: Boolean,
    val mustGenerateData: Boolean
) {

    companion object {

        val CONFIGURATION_NAMESPACE = "com.isirode.products.api";

        // TODO : mutualise it (used in deploy & here)
        fun loadConfiguration(secondaryFile: String?): Configuration {
            val applicationConfAsString = this::class.java.classLoader.getResource("application.conf")?.readText(Charsets.UTF_8)
                ?: throw RuntimeException("application.conf must be present")
            val applicationConf = ConfigFactory.parseString(applicationConfAsString.trimMargin())

            if (!secondaryFile.isNullOrEmpty()) {
                val priorityConfAsString = File(secondaryFile).readText(Charsets.UTF_8)
                val priorityConf = ConfigFactory.parseString(priorityConfAsString)
                val finalConf = priorityConf.resolveWith(applicationConf)

                return finalConf.extract(CONFIGURATION_NAMESPACE)
            }

            return applicationConf.extract(CONFIGURATION_NAMESPACE)
        }
    }

}