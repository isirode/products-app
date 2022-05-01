package com.isirode.products.java.schema.orm.orientdb.mappings.software

import com.orientechnologies.orient.core.record.OVertex
import com.orientechnologies.orient.core.sql.executor.OResult
import com.isirode.products.java.schema.pojos.Product
import com.isirode.products.java.schema.pojos.software.Library

class LibraryMapping() : SoftwareProductMapping() {

    override fun persist(vertex: OVertex, product: Product) {
        super.persist(vertex, product)
        if (product is Library) {
            vertex.setProperty(Library::writtenIn.name, product.writtenIn)
            vertex.setProperty(Library::writtenFor.name, product.writtenFor)
        }
    }

    override fun map(rs: OResult, product: Product) {
        super.map(rs, product)
        if (product is Library) {
            product.writtenIn = rs.getProperty(Library::writtenIn.name)
            product.writtenFor = rs.getProperty(Library::writtenFor.name)
        }
    }

}