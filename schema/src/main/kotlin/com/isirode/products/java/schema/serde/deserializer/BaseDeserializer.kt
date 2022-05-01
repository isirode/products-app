package com.isirode.products.java.schema.serde.deserializer

import com.isirode.products.java.schema.pojos.software.Language
import com.isirode.products.java.schema.pojos.software.Library

open class BaseDeserializer {

    // FIXME : check extends Product, make it generic, probably need to use reified keyword
    val availableTypes: MutableMap<String, Class<*>> = HashMap()

    // FIXME : make it an annotation, gonna be hard to maintain
    init {
        availableTypes["software.library"] = Library::class.java
        availableTypes["software.language"] = Language::class.java
    }

}