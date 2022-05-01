package com.isirode.products.java.schema

import com.isirode.products.java.schema.commands.GenerateJsonSchemas
import com.isirode.products.java.schema.commands.GenerateOrientDBData
import com.isirode.products.java.schema.commands.GenerateOrientDBSchema
import kotlinx.cli.*

@OptIn(ExperimentalCli::class)
fun main(args: Array<String>) {

    val parser = ArgParser("schema")

    val generateOrientDBSchema = GenerateOrientDBSchema()
    val generateJsonSchemas = GenerateJsonSchemas()
    val generateOrientDBData = GenerateOrientDBData()

    parser.subcommands(generateOrientDBSchema, generateJsonSchemas, generateOrientDBData)

    parser.parse(args)

}
