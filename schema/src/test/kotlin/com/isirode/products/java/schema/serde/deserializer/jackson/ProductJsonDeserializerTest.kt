package com.isirode.products.java.schema.serde.deserializer.jackson

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Test
import com.isirode.products.java.schema.pojos.ConcreteBaseProduct
import com.isirode.products.java.schema.pojos.Product
import com.isirode.products.java.schema.pojos.software.Library
import com.isirode.products.java.schema.serde.deserializer.BaseDeserializer

class ProductJsonDeserializerTest {

    // TODO : add JSONC and test it ?
    // mapper.enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES);

    @Test
    fun shouldAssignUnknownPropertiesIfDefaultDeserializer() {
        // given
        val json = """
            {
                "name": "John", 
                "type": "Unknown",
                "test": {
                    "test": 10,
                    "othertest": "test",
                    "someobject": {
                        "test": 100
                    }
                }
            }"""
        val mapper = ObjectMapper().registerModule(KotlinModule())

        // when
        val product: Product = mapper.readValue(json)

        // then
        assert(product.properties.isNotEmpty())
        assert(product.properties["test"] != null)
    }

    @Test
    fun shouldDeserializeToCorrectTypeIfDeserializerIsProvided() {
        // given
        val json = """
            {
                "type": "software.library",
                "name": "John",
                "testing": "test",
                "test": {
                    "test": 10,
                    "othertest": "test",
                    "someobject": {
                        "test": 100
                    }
                }
            }"""
        // TODO : provide a module with the lib
        val productModule = SimpleModule()
        val baseDeserializer = BaseDeserializer()
        productModule.addDeserializer(Product::class.java, ProductJsonDeserializer(baseDeserializer))

        val mapper = ObjectMapper()
        mapper.registerModule(productModule)
        mapper.registerModule(KotlinModule())

        // when
        val product: Product = mapper.readValue(json)

        // then
        assert(product.properties.isNotEmpty())
        assert(product is Library)
    }

    @Test
    fun shouldDeserializeToProductIfTypeIsNotProvided() {
        // given
        val json = """
            {
                "name": "John",
                "testing": "test",
                "test": {
                    "test": 10,
                    "othertest": "test",
                    "someobject": {
                        "test": 100
                    }
                }
            }"""
        // TODO : provide a module with the lib
        val productModule = SimpleModule()
        val baseDeserializer = BaseDeserializer()
        productModule.addDeserializer(Product::class.java, ProductJsonDeserializer(baseDeserializer))

        val mapper = ObjectMapper()
        mapper.registerModule(productModule)
        mapper.registerModule(KotlinModule())

        // when
        val product: Product = mapper.readValue(json)

        // then
        assert(product.properties.isNotEmpty())
        assert(product::class.java == ConcreteBaseProduct::class.java)
    }

    @Test
    fun shouldDeserializeToProductIfTypeIsUnknown() {
        // given
        val json = """
            {
                "type": "software.unknown",
                "name": "John",
                "testing": "test",
                "test": {
                    "test": 10,
                    "othertest": "test",
                    "someobject": {
                        "test": 100
                    }
                }
            }"""
        // TODO : provide a module with the lib
        val productModule = SimpleModule()
        val baseDeserializer = BaseDeserializer()
        productModule.addDeserializer(Product::class.java, ProductJsonDeserializer(baseDeserializer))

        val mapper = ObjectMapper()
        mapper.registerModule(productModule)
        mapper.registerModule(KotlinModule())

        // when
        val product: Product = mapper.readValue(json)

        // then
        assert(product.properties.isNotEmpty())
        assert(product::class.java == ConcreteBaseProduct::class.java)
    }

    @Test
    fun shouldBeAbleToAddProductSubType() {
        // given
        class Testing : Product() {
            var newProperty: String = ""
        }
        val json = """
            {
                "type": "software.testing",
                "name": "John",
                "newProperty": "testing",
                "test": {
                    "test": 10,
                    "othertest": "test",
                    "someobject": {
                        "test": 100
                    }
                }
            }"""
        // TODO : provide a module with the lib
        val productModule = SimpleModule()
        val baseDeserializer = BaseDeserializer()
        baseDeserializer.availableTypes.put("software.testing", Testing::class.java)
        productModule.addDeserializer(Product::class.java, ProductJsonDeserializer(baseDeserializer))

        val mapper = ObjectMapper()
        mapper.registerModule(productModule)
        mapper.registerModule(KotlinModule())

        // when
        val product: Product = mapper.readValue(json)

        // then
        assert(product.properties.isNotEmpty())
        assert(product is Testing)
        val testing = product as Testing
        assert(testing.newProperty == "testing")
    }

}