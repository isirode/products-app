package com.isirode.products.java.schema.orm.orientdb.mappings

import com.orientechnologies.orient.core.record.OVertex
import com.orientechnologies.orient.core.sql.executor.OResult
import com.isirode.products.java.schema.pojos.Product

// TODO : use annotation for this ?
open class ProductMapping : Mapping {

    override fun persist(vertex: OVertex, product: Product) {
        if (product.name.isNullOrEmpty()) {
            throw RuntimeException("${Product::class.java}::${Product::name.name} is mandatory")
        }
        if (product.type.isNullOrEmpty()) {
            throw RuntimeException("${Product::class.java}::${Product::type.name} is mandatory")
        }
        vertex.setProperty(Product::name.name, product.name)
        vertex.setProperty(Product::type.name, product.type)
        vertex.setProperty(Product::description.name, product.description)
        vertex.setProperty(Product::properties.name, product.properties)
    }

    override fun map(rs: OResult, product: Product) {
        product.name = rs.getProperty(Product::name.name)
        product.type = rs.getProperty(Product::type.name)
        product.description = rs.getProperty(Product::description.name)
        product.properties = rs.getProperty(Product::properties.name)
    }

}