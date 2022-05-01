package com.isirode.products.java.schema.orm.orientdb.mappings

import com.orientechnologies.orient.core.record.OVertex
import com.orientechnologies.orient.core.sql.executor.OResult
import com.isirode.products.java.schema.pojos.Product

interface Mapping {

    fun persist(vertex: OVertex, product: Product)
    fun map(rs: OResult, product: Product)

}