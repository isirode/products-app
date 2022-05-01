package com.isirode.products.java.schema.services

import com.isirode.products.java.schema.jsonSchema.Generator
import com.isirode.products.java.schema.pojos.Product
import com.isirode.products.java.schema.pojos.software.Language
import com.isirode.products.java.schema.pojos.software.Library
import java.io.File
import java.util.*

class GenerateJsonSchemasService {

    fun execute(folderPath: String) {

        println("Execute ${GenerateJsonSchemasService::class.simpleName}::${this::execute.name}")

        val generator = Generator()
        val classes = listOf<Class<*>>(Product::class.java, Language::class.java, Library::class.java)
        classes.forEach {
            println("Generating Json schema file of ${it.name}")

            val filename = folderPath + "/${it.simpleName.lowercase(Locale.getDefault())}.schema.json"
            println("Filename will be: $filename")

            val schema = generator.generate(it)
            File(filename).writeText(schema)

            println("Done generating Json schema file of ${it.name}")
        }
    }

}