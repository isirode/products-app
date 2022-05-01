package com.isirode.products.java.schema.jsonSchema

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import com.isirode.products.java.schema.pojos.Product
import com.isirode.products.java.schema.pojos.software.Language
import com.isirode.products.java.schema.pojos.software.Library
import com.isirode.products.java.schema.pojos.software.SoftwareProduct
import java.util.stream.StreamSupport
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberProperties
import kotlin.streams.toList

class GeneratorTest {

    private val mapper = ObjectMapper()
    private val generator = Generator()

    @Test
    fun schemaOfProductShouldBeCorrect() {
        // given
        val schemaAsString: String = generator.generate<Product>()

        // when
        val schemaAsJsonNode: JsonNode = mapper.readTree(schemaAsString)

        // then
        assertThat(schemaAsJsonNode.path("title").asText()).isEqualTo(Product::class.java.simpleName)
        assertThat(schemaAsJsonNode.path("type").asText()).isEqualTo("object")

        val properties: JsonNode = schemaAsJsonNode.path("properties")
        assertPropertiesOfProductAreCorrectlyDefined(properties)

        assertRequiredPropertiesOfProductArePresent(schemaAsJsonNode)
    }

    @Test
    fun schemaOfLibraryShouldBeCorrect() {
        // given
        val schemaAsString: String = generator.generate<Library>()

        // when
        val schemaAsJsonNode: JsonNode = mapper.readTree(schemaAsString)

        // then
        assertThat(schemaAsJsonNode.path("title").asText()).isEqualTo(Library::class.java.simpleName)
        assertThat(schemaAsJsonNode.path("type").asText()).isEqualTo("object")

        // We cannot use Library::class.memberProperties here, too complicated
        // Tried to use something like this, but it required more work for "properties" and other uncommon types
        // private val javaTypeToSchemaType: Map<String, String> = mapOf(
        //    String::class.java.typeName to "string",
        //    Object::class.java.typeName to "object"
        // )
        val properties: JsonNode = schemaAsJsonNode.path("properties")

        assertThat(properties.path(Library::writtenIn.name).isMissingNode).isFalse
        assertThat(properties.path(Library::writtenIn.name).path("type").asText()).isEqualTo("string")

        assertThat(properties.path(Library::writtenFor.name).isMissingNode).isFalse
        assertThat(properties.path(Library::writtenFor.name).path("type").asText()).isEqualTo("string")

        assertPropertiesOfSoftwareProductAreCorrectlyDefined(properties)

        assertPropertiesOfProductAreCorrectlyDefined(properties)
    }

    @Test
    fun schemaOfLanguageShouldBeCorrect() {
        // given
        val schemaAsString: String = generator.generate<Language>()

        // when
        val schemaAsJsonNode: JsonNode = mapper.readTree(schemaAsString)

        // then
        assertThat(schemaAsJsonNode.path("title").asText()).isEqualTo(Language::class.java.simpleName)
        assertThat(schemaAsJsonNode.path("type").asText()).isEqualTo("object")

        // We cannot use Library::class.memberProperties here, too complicated
        // Tried to use something like this, but it required more work for "properties" and other uncommon types
        // private val javaTypeToSchemaType: Map<String, String> = mapOf(
        //    String::class.java.typeName to "string",
        //    Object::class.java.typeName to "object"
        // )
        val properties: JsonNode = schemaAsJsonNode.path("properties")

        assertThat(properties.path(Language::imperative.name).isMissingNode).isFalse
        assertThat(properties.path(Language::imperative.name).path("type").asText()).isEqualTo("boolean")

        assertPropertiesOfSoftwareProductAreCorrectlyDefined(properties)

        assertPropertiesOfProductAreCorrectlyDefined(properties)

        // assertThat(schemaAsString).isEqualTo("toto")
    }

    private fun assertPropertiesOfProductAreCorrectlyDefined(properties: JsonNode) {
        assertThat(properties.path(Product::type.name).isMissingNode).isFalse
        assertThat(properties.path(Product::type.name).path("type").asText()).isEqualTo("string")

        assertThat(properties.path(Product::name.name).isMissingNode).isFalse
        assertThat(properties.path(Product::name.name).path("type").asText()).isEqualTo("string")

        assertThat(properties.path(Product::description.name).isMissingNode).isFalse
        assertThat(properties.path(Product::description.name).path("type").asText()).isEqualTo("string")

        assertThat(properties.path(Product::properties.name).isMissingNode).isFalse
        assertThat(properties.path(Product::properties.name).path("type").asText()).isEqualTo("object")
    }

    private fun assertRequiredPropertiesOfProductArePresent(properties: JsonNode) {
        assertThat(properties.path("required").isMissingNode).isFalse
        val requiredPropertiesAsArrayNode: ArrayNode = properties.path("required") as ArrayNode
        val requiredPropertiesAsStringList = StreamSupport.stream(requiredPropertiesAsArrayNode.spliterator(), false)
            .map { it.asText() }
            .toList()

        val requiredProperties = Product::class.memberProperties.stream()
            .filter {
                val jsonAnnotation: JsonProperty? = it.findAnnotation()
                if (jsonAnnotation == null) return@filter false
                if (!jsonAnnotation.required) return@filter false

                true
            }
            .map {
                it.name
            }.toList()
        assertThat(requiredPropertiesAsStringList).containsAll(requiredProperties)
    }

    private fun assertPropertiesOfSoftwareProductAreCorrectlyDefined(properties: JsonNode) {
        assertThat(properties.path(SoftwareProduct::officialWebsiteURL.name).isMissingNode).isFalse
        assertThat(properties.path(SoftwareProduct::officialWebsiteURL.name).path("type").asText()).isEqualTo("string")

        assertThat(properties.path(SoftwareProduct::officialSourceURL.name).isMissingNode).isFalse
        assertThat(properties.path(SoftwareProduct::officialSourceURL.name).path("type").asText()).isEqualTo("string")

        assertThat(properties.path(SoftwareProduct::officialDocumentationURL.name).isMissingNode).isFalse
        assertThat(properties.path(SoftwareProduct::officialDocumentationURL.name).path("type").asText()).isEqualTo("string")

        assertThat(properties.path(SoftwareProduct::wikipediaURL.name).isMissingNode).isFalse
        assertThat(properties.path(SoftwareProduct::wikipediaURL.name).path("type").asText()).isEqualTo("string")
    }

}