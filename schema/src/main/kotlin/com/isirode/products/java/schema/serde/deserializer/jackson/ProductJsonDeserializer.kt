package com.isirode.products.java.schema.serde.deserializer.jackson

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.*
import com.isirode.products.java.schema.pojos.ConcreteBaseProduct
import com.isirode.products.java.schema.pojos.Product
import com.isirode.products.java.schema.serde.deserializer.BaseDeserializer

class ProductJsonDeserializer(val baseDeserializer: BaseDeserializer) : JsonDeserializer<Product>() {

    override fun deserialize(parser: JsonParser, ctxt: DeserializationContext): Product {
        val root: JsonNode = parser.readValueAsTree()
        val type: JsonNode? = root.get(Product::type.name)
        var productClass: Class<*>? = null
        if (type != null && !type.isMissingNode && !type.isNull) {
            val typeAsString = type.textValue()
            productClass = baseDeserializer.availableTypes[typeAsString]
        }
        if (productClass == null) {
            productClass = ConcreteBaseProduct::class.java
        }
        val product = parser.codec.treeToValue(root, productClass)
        return product as Product
    }

}