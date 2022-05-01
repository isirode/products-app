package com.isirode.products.java.schema.commands

import com.isirode.products.java.schema.services.GenerateJsonSchemasService
import kotlinx.cli.ArgType
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand
import kotlinx.cli.required

@OptIn(ExperimentalCli::class)
class GenerateJsonSchemas: Subcommand("generate-json-schemas", "Generate Json Schemas") {

    val folderPath by option(ArgType.String, "folder-path", description = "Base path where to put Json schemas")
        .required()

    override fun execute() {
        GenerateJsonSchemasService().execute(folderPath)
    }
}