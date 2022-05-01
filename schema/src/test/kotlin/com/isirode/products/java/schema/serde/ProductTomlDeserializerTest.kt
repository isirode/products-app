package com.isirode.products.java.schema.serde

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.toml.TomlFactory
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.junit.jupiter.api.Test
import com.isirode.products.java.schema.pojos.ConcreteBaseProduct
import com.isirode.products.java.schema.pojos.Product
import com.isirode.products.java.schema.pojos.software.Library
import com.isirode.products.java.schema.serde.deserializer.BaseDeserializer
import com.isirode.products.java.schema.serde.deserializer.ProductTomlDeserializer

// FIXME : all the tests are similarly construct as ProductJsonDeserializerTest
// Furthermore all deserializer should follow same functionalities
// It should be put in common and tested
// Differences: the deserializer, the text input
class ProductTomlDeserializerTest {

    // TODO : test round trip somewhere else

    @Test
    fun shouldAssignUnknownPropertiesIfDefaultDeserializer() {
        // given
        val toml =
            """
            type = "Unknown"
            name = "somelib"
            [test]
            test = "test"
            """
        val productJsonDeserializer = ProductTomlDeserializer.getDefaultTomlDeserializer()

        // when
        val product = productJsonDeserializer.deserialize(toml)

        // then
        assert(product.properties.isNotEmpty())
        assert(product.properties["test"] != null)
    }

    @Test
    fun shouldDeserializeToCorrectTypeIfDeserializerIsProvided() {
        // given
        val toml =
            """
            type = "software.library"
            name = "somelib"
            [test]
            test = "test"
            """
        val productJsonDeserializer = ProductTomlDeserializer.getDefaultTomlDeserializer()

        // when
        val product = productJsonDeserializer.deserialize(toml)

        // then
        assert(product.properties.isNotEmpty())
        assert(product is Library)
    }

    @Test
    fun shouldDeserializeToProductIfTypeIsNotProvided() {
        // given
        val toml =
            """
            name = "somelib"
            [test]
            test = "test"
            """
        val productJsonDeserializer = ProductTomlDeserializer.getDefaultTomlDeserializer()

        // when
        val product = productJsonDeserializer.deserialize(toml)

        // then
        assert(product.properties.isNotEmpty())
        assert(product is ConcreteBaseProduct)
    }

    @Test
    fun shouldDeserializeToProductIfTypeIsUnknown() {
        // given
        val toml =
            """
            type = "software.unknown"
            name = "somelib"
            [test]
            test = "test"
            """
        val productJsonDeserializer = ProductTomlDeserializer.getDefaultTomlDeserializer()

        // when
        val product = productJsonDeserializer.deserialize(toml)

        // then
        assert(product.properties.isNotEmpty())
        assert(product is ConcreteBaseProduct)
    }

    @Test
    fun shouldBeAbleToAddProductSubType() {
        // given
        class Testing : Product() {
            var newProperty: String = ""
        }
        val toml =
            """
            type = "software.testing"
            name = "somelib"
            newProperty = "testing"
            [test]
            test = "test"
            """
        // TODO : do something about this repetition of code from ProductTomlDeserializer.getDefaultTomlDeserializer()
        val factory = TomlFactory()
        val mapper = ObjectMapper(factory)
        mapper.registerModule(KotlinModule())
        val baseDeserializer = BaseDeserializer()
        baseDeserializer.availableTypes.put("software.testing", Testing::class.java)
        val productJsonDeserializer = ProductTomlDeserializer(mapper, baseDeserializer)

        // when
        val product = productJsonDeserializer.deserialize(toml)

        // then
        assert(product.properties.isNotEmpty())
        assert(product is Testing)
        val testing = product as Testing
        assert(testing.newProperty == "testing")
    }

}