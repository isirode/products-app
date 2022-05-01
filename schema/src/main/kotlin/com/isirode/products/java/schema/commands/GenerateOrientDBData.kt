package com.isirode.products.java.schema.commands

import com.isirode.products.java.schema.services.GenerateOrientDBDataService
import kotlinx.cli.*

@OptIn(ExperimentalCli::class)
class GenerateOrientDBData: Subcommand("generate-orientdb-data", "Insert data in OrientDB") {

    // TODO : this is a duplicate, handle this
    val serverUrl by option(ArgType.String, "url", description = "Server url")
        .default("remote:localhost")
    val database by option(ArgType.String, "database",  description = "OrientDB server username")
        .default("products")
    val serverUsername by option(ArgType.String, "server-username", description = "OrientDB server username")
        .default("root")
    val serverPassword by option(ArgType.String, "server-password", "OrientDB server password of specified username")
        .required()

    val folderPath by option(ArgType.String, "folder-path", description = "Base path where search for TOML data")
        .required()

    // TODO :
    // clear before insert ?
    // handling duplicate ?

    override fun execute() {
        GenerateOrientDBDataService().execute(serverUrl, database, serverUsername, serverPassword, folderPath)
    }
}