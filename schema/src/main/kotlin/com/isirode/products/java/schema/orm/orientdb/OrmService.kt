package com.isirode.products.java.schema.orm.orientdb

import com.isirode.products.java.schema.orm.orientdb.mappings.Mapping
import com.isirode.products.java.schema.orm.orientdb.mappings.ProductMapping
import com.isirode.products.java.schema.orm.orientdb.mappings.software.LanguageMapping
import com.isirode.products.java.schema.orm.orientdb.mappings.software.LibraryMapping
import com.orientechnologies.orient.core.db.ODatabaseSession
import com.orientechnologies.orient.core.record.ORecord
import com.orientechnologies.orient.core.sql.executor.OResult
import com.isirode.products.java.schema.pojos.Product
import com.isirode.products.java.schema.pojos.software.Language
import com.isirode.products.java.schema.pojos.software.Library
import com.isirode.products.java.schema.serde.deserializer.BaseDeserializer

// TODO : use caffeine to cache data
// TODO : use annotation to persist the data
// TODO : handle transactions
// FIXME : MigrationService use ODatabaseSession in parameter, here its a property, pick one choice and use it in both service
class OrmService(var db: ODatabaseSession) {

    // FIXME : move BaseDeserializer or/and rename it
    val baseDeserializer = BaseDeserializer()
    var mappings: Map<Class<*>, Mapping> = hashMapOf(
        Product::class.java to ProductMapping(),
        Library::class.java to LibraryMapping(),
        Language::class.java to LanguageMapping()
    )

    fun persist(product: Product) {
        // TODO : better handling of this
        if (product is Library) {
            persist(product)
            return
        }
        if (product is Language) {
            persist(product)
            return
        }
        val vertex = db.newVertex(Product::class.simpleName)
        val mapping = ProductMapping()
        mapping.persist(vertex, product)
        vertex.save<ORecord>()
    }

    fun persist(product: Library) {
        val vertex = db.newVertex(Library::class.simpleName)
        val mapping = LibraryMapping()
        mapping.persist(vertex, product)
        vertex.save<ORecord>()
    }

    fun persist(product: Language) {
        val vertex = db.newVertex(Language::class.simpleName)
        val mapping = LanguageMapping()
        mapping.persist(vertex, product)
        vertex.save<ORecord>()
    }

    fun getOne(name: String): Product? {
        val query = "SELECT FROM Product where name = ?"
        val rs = db.query(query, name)
        var product: Product? = null
        while (rs.hasNext()) {
            val item = rs.next()
            var type: String? = item.getProperty(Product::type.name)
            if (type == null) {
                product = Product()
            } else {
                val _class: Class<*>? = baseDeserializer.availableTypes[type]
                if (_class == null) {
                    product = Product()
                } else {
                    product = _class.getConstructor().newInstance() as Product?
                }
            }
            // FIXME : find something more elegant
            if (product != null) {
                map(item, product)
                break// we stop at the first correct element
            }
        }
        rs.close()
        return product
    }

    fun queryMultiple(query: String): List<Product> {
        val rs = db.query(query)
        val productList = mutableListOf<Product>()
        var product: Product? = null

        while (rs.hasNext()) {
            val item = rs.next()
            val type: String? = item.getProperty(Product::type.name)
            if (type == null) {
                product = Product()
            } else {
                val _class: Class<*>? = baseDeserializer.availableTypes[type]
                if (_class == null) {
                    product = Product()
                } else {
                    product = _class.getConstructor().newInstance() as Product?
                }
            }
            // FIXME : find something more elegant
            if (product != null) {
                map(item, product)

                productList.add(product)
            }
        }
        rs.close()
        return productList
    }

    // FIXME : can get the correct implementation and not repeat the map part ?
    fun map(rs: OResult, product: Product) {
        mappings[product.javaClass]?.map(rs, product)
    }

    // cannot enter this
    fun map(rs: OResult, product: Library) {
        val mapping = LibraryMapping()
        mapping.map(rs, product)
    }

}