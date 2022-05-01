package com.isirode.products.java.schema.commands

import com.isirode.products.java.schema.migrations.MigrationDirection
import com.isirode.products.java.schema.services.GenerateOrientDBSchemaService
import kotlinx.cli.*

@OptIn(ExperimentalCli::class)
class GenerateOrientDBSchema: Subcommand("generate-orientdb-schema", "Generate schema in OrientDB") {

    // TODO : this is duplicated, but hard to fix without multi-inheritance
    // something like @Delegate of Lombok might work
    // or call the constructor of Subcommand at this level

    val serverUrl by option(ArgType.String, "url", description = "Server url, must include the 'remote:' part if necessary")
        .default("remote:localhost")
    val database by option(ArgType.String, "database",  description = "OrientDB server username")
        .default("products")
    val serverUsername by option(ArgType.String, "server-username", description = "OrientDB server username")
        .default("root")
    val serverPassword by option(ArgType.String, "server-password", "OrientDB server password of specified username")
        .required()
    val readerUsername by option(ArgType.String, "reader-username", "OrientDB reader username")
        .default("reader")
    val readerPassword by option(ArgType.String, "reader-password", "OrientDB reader password of specified reader username")
        .required()

    val direction by option(ArgType.Choice<MigrationDirection>(), "direction", shortName = "d", description = "Direction of the migration")
        .default(MigrationDirection.UP)

    override fun execute() {
        GenerateOrientDBSchemaService().execute(serverUrl, database, serverUsername, serverPassword, readerUsername, readerPassword, direction)
    }
}