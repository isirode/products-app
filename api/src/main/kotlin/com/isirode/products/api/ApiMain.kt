package com.isirode.products.api

import com.isirode.products.api.configuration.Configuration
import com.isirode.products.api.plugins.configureHTTP
import com.isirode.products.api.plugins.configureRouting
import com.isirode.products.api.plugins.configureSecurity
import com.isirode.products.api.plugins.configureSerialization
import com.isirode.products.java.schema.migrations.MigrationDirection
import com.isirode.products.java.schema.services.GenerateOrientDBDataService
import com.isirode.products.java.schema.services.GenerateOrientDBSchemaService
import io.ktor.server.engine.*
import com.isirode.products.api.services.ProductService
import com.orientechnologies.orient.core.config.OGlobalConfiguration
import com.orientechnologies.orient.core.db.ODatabasePool
import com.orientechnologies.orient.core.db.OrientDB
import com.orientechnologies.orient.core.db.OrientDBConfig
import io.ktor.server.netty.*
import org.apache.logging.log4j.LogManager

// TODO : offer an option to rollback OrientDB at the end of the program
// using a shutdown hook maybe

// TODO : use arguments or file instead of environment variables whenever possible
// use conf file for not essential settings
fun main() {

    val logger = LogManager.getLogger({}.javaClass.enclosingMethod.name)

    logger.info("Initializing API")

    // TODO : make the variables nullable and put a default
    // ?:

    val priorityConfFileName: String? = System.getenv("CONFIGURATION_FILENAME")

    val configuration: Configuration = Configuration.loadConfiguration(priorityConfFileName)

    val serverUrl = "remote:${configuration.orientDbHostName}"

    val serverUsername = "root"

    // TODO : use a CLI argument also ?
    val mustGenerateSchema = configuration.mustGenerateSchema
    val mustGenerateData = configuration.mustGenerateData

    // TODO : offer choice to migrate or no
    // Generating schema
    if (mustGenerateSchema) {
        GenerateOrientDBSchemaService().execute(
            serverUrl, configuration.databaseName,
            serverUsername, configuration.orientDbPassword,
            configuration.readerUsername, configuration.readerPassword,
            MigrationDirection.UP
        )
    }
    // Inserting data
    if (mustGenerateData) {
        GenerateOrientDBDataService().execute(
            serverUrl, configuration.databaseName,
            serverUsername, configuration.orientDbPassword,
            configuration.dataFolderPath
        )
    }

    val orientDB = OrientDB(serverUrl, serverUsername, configuration.orientDbPassword, OrientDBConfig.defaultConfig())

    // TODO : make it a configuration settings
    val poolCfg = OrientDBConfig.builder()
    poolCfg.addConfig(OGlobalConfiguration.DB_POOL_MIN, 5)
    poolCfg.addConfig(OGlobalConfiguration.DB_POOL_MAX, 10)

    // Info: we cannot use standard ODatabaseSession here
    val pool = ODatabasePool(
        orientDB, configuration.databaseName,
        configuration.readerUsername, configuration.readerPassword,
        poolCfg.build()
    )

    val productService = ProductService(pool)

    logger.info("Starting API to port {}", configuration.apiPort)

    embeddedServer(Netty, port = configuration.apiPort, host = "0.0.0.0") {
        configureRouting(productService)
        configureSerialization()
        configureHTTP()
        configureSecurity()
    }.start(wait = true)
}
