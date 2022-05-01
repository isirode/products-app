package com.isirode.products.java.schema.pojos

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import com.isirode.products.java.schema.pojos.software.Language
import com.isirode.products.java.schema.pojos.software.Library
import com.isirode.products.java.schema.pojos.software.SoftwareProduct

class ProductTest {

    @Test
    fun productShouldBeEqualIfAllFieldsAreEqual() {
        // given
        val firstProduct = Product()
        firstProduct.name = "test"
        firstProduct.properties = hashMapOf("test" to "test") as Map<String, Any>?

        // when
        val secondProduct = Product()
        secondProduct.name = "test"
        secondProduct.properties = hashMapOf("test" to "test") as Map<String, Any>?

        // then
        assertThat(firstProduct).isEqualTo(secondProduct)
    }

    @Test
    fun softwareProductShouldBeEqualIfAllFieldsAreEqual() {
        // given
        val firstProduct = SoftwareProduct()
        firstProduct.name = "test"
        firstProduct.properties = hashMapOf("test" to "test") as Map<String, Any>?
        firstProduct.wikipediaURL = "test"

        // when
        val secondProduct = SoftwareProduct()
        secondProduct.name = "test"
        secondProduct.properties = hashMapOf("test" to "test") as Map<String, Any>?
        secondProduct.wikipediaURL = "test"

        // then
        assertThat(firstProduct).isEqualTo(secondProduct)
    }

    @Test
    fun libraryShouldBeEqualIfAllFieldsAreEqual() {
        // given
        val firstProduct = Library()
        firstProduct.name = "test"
        firstProduct.properties = hashMapOf("test" to "test") as Map<String, Any>?
        firstProduct.writtenIn = "test"

        // when
        val secondProduct = Library()
        secondProduct.name = "test"
        secondProduct.properties = hashMapOf("test" to "test") as Map<String, Any>?
        secondProduct.writtenIn = "test"

        // then
        assertThat(firstProduct).isEqualTo(secondProduct)
    }

    // TODO : test fields of language
    @Test
    fun languageShouldBeEqualIfAllFieldsAreEqual() {
        // given
        val firstProduct = Language()
        firstProduct.name = "test"
        firstProduct.properties = hashMapOf("test" to "test") as Map<String, Any>?

        // when
        val secondProduct = Language()
        secondProduct.name = "test"
        secondProduct.properties = hashMapOf("test" to "test") as Map<String, Any>?

        // then
        assertThat(firstProduct).isEqualTo(secondProduct)
    }

}