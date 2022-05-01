package com.isirode.products.java.schema.orm.orientdb.mappings.software

import com.orientechnologies.orient.core.record.OVertex
import com.orientechnologies.orient.core.sql.executor.OResult
import com.isirode.products.java.schema.pojos.Product
import com.isirode.products.java.schema.pojos.software.Language

class LanguageMapping() : SoftwareProductMapping() {

    override fun persist(vertex: OVertex, product: Product) {
        super.persist(vertex, product)
        if (product is Language) {
            vertex.setProperty(Language::imperative.name, product.imperative)
        }
    }

    override fun map(rs: OResult, product: Product) {
        super.map(rs, product)
        if (product is Language) {
            product.imperative = rs.getProperty(Language::imperative.name)
        }
    }

}