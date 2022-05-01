package com.isirode.products.java.schema.jsonSchema

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.kjetland.jackson.jsonSchema.JsonSchemaConfig
import com.kjetland.jackson.jsonSchema.JsonSchemaGenerator

// Info : we are using https://github.com/mbknor/mbknor-jackson-jsonSchema
class Generator {

    // TODO : configure main properties
    // TODO : maybe use Kotlin builder
    inline fun <reified T> generate(): String {
        val classz = T::class.java
        return generate(classz)
    }

    fun generate(classz: Class<*>): String {
        val objectMapper = ObjectMapper()
        val config = JsonSchemaConfig.vanillaJsonSchemaDraft4().withFailOnUnknownProperties(false)
        val jsonSchemaGenerator = JsonSchemaGenerator(objectMapper, config)
        val jsonSchema: JsonNode = jsonSchemaGenerator.generateJsonSchema(classz)
        val jsonSchemaAsString: String = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonSchema)
        return jsonSchemaAsString
    }

}