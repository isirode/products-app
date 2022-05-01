package com.isirode.products.java.schema.orm.orientdb.mappings.software

import com.isirode.products.java.schema.orm.orientdb.mappings.ProductMapping
import com.orientechnologies.orient.core.record.OVertex
import com.orientechnologies.orient.core.sql.executor.OResult
import com.isirode.products.java.schema.pojos.Product
import com.isirode.products.java.schema.pojos.software.SoftwareProduct

open class SoftwareProductMapping() : ProductMapping() {

    override fun persist(vertex: OVertex, product: Product) {
        super.persist(vertex, product)
        if (product is SoftwareProduct) {
            vertex.setProperty(SoftwareProduct::officialWebsiteURL.name, product.officialWebsiteURL)
            vertex.setProperty(SoftwareProduct::officialSourceURL.name, product.officialSourceURL)
            vertex.setProperty(SoftwareProduct::officialDocumentationURL.name, product.officialDocumentationURL)
            vertex.setProperty(SoftwareProduct::wikipediaURL.name, product.wikipediaURL)
        }
    }

    override fun map(rs: OResult, product: Product) {
        super.map(rs, product)
        if (product is SoftwareProduct) {
            product.officialWebsiteURL = rs.getProperty(SoftwareProduct::officialWebsiteURL.name)
            product.officialSourceURL = rs.getProperty(SoftwareProduct::officialSourceURL.name)
            product.officialDocumentationURL = rs.getProperty(SoftwareProduct::officialDocumentationURL.name)
            product.wikipediaURL = rs.getProperty(SoftwareProduct::wikipediaURL.name)
        }
    }

}