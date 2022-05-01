package com.isirode.products.java.schema.serde.deserializer

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.toml.TomlFactory
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.isirode.products.java.schema.pojos.ConcreteBaseProduct
import com.isirode.products.java.schema.pojos.Product

// FIXME : will not work with Jackson's JsonDeserializer system, report it to https://github.com/FasterXML/jackson-dataformats-text
class ProductTomlDeserializer(val mapper: ObjectMapper, val baseDeserializer: BaseDeserializer) {

    companion object {
        fun getDefaultTomlDeserializer(): ProductTomlDeserializer {
            val factory = TomlFactory()
            val mapper = ObjectMapper(factory)
            mapper.registerModule(KotlinModule())
            val baseDeserializer = BaseDeserializer()

            return ProductTomlDeserializer(mapper, baseDeserializer)
        }
    }

    fun deserialize(json: String): Product {
        val root: JsonNode = mapper.readTree(json)
        // FIXME : all of this similar to ProductJsonDeserializer's code
        val type: JsonNode? = root.get(Product::type.name)
        var pojoClass: Class<*>? = null
        if (type != null && !type.isMissingNode && !type.isNull) {
            val typeAsString = type.textValue()
            pojoClass = baseDeserializer.availableTypes[typeAsString]
        }
        if (pojoClass == null) {
            pojoClass = ConcreteBaseProduct::class.java
        }
        // end FIXME
        val product = mapper.treeToValue(root, pojoClass) as Product
        return product
    }

}