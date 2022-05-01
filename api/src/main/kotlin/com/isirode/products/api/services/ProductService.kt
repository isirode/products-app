package com.isirode.products.api.services

import com.isirode.products.java.schema.orm.orientdb.OrmService
import com.orientechnologies.orient.core.db.ODatabasePool
import com.isirode.products.java.schema.pojos.Product

class ProductService(val pool: ODatabasePool) {

    // TODO : interface, move this somewhere else ?
    fun getOne(name: String): Product? {

        pool.acquire().use {
            return OrmService(it).getOne(name)
        }

        // return ormService.getOne(name)
    }

    fun queryMultiple(query: String): List<Product> {
        pool.acquire().use {
            return OrmService(it).queryMultiple(query)
        }
    }

}