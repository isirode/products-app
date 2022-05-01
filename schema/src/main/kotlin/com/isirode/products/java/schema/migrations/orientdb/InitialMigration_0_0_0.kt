package com.isirode.products.java.schema.migrations.orientdb

import com.isirode.products.java.schema.migrations.Migration
import com.orientechnologies.orient.core.db.ODatabaseSession
import com.orientechnologies.orient.core.metadata.schema.OClass
import com.orientechnologies.orient.core.metadata.schema.OType
import com.isirode.products.java.schema.pojos.Product
import com.isirode.products.java.schema.pojos.software.Language
import com.isirode.products.java.schema.pojos.software.Library

// FIXME : replace this system with a KSP Annotation processor ?
// TODO : do some kind of operator handling those at the same place
class InitialMigration_0_0_0 : Migration {
    override fun up(db: ODatabaseSession) {
        var product = db.getClass(Product::class.simpleName)
        if (product == null) {
            product = db.createVertexClass(Product::class.simpleName)

            // FIXME : need to fix the schema ?
            val type = product?.createProperty(Product::type.name, OType.STRING)
            // FIXME : not working
            if (type != null) {
                type.isMandatory = true
                type.isNotNull = true
            }
            val name = product?.createProperty(Product::description.name, OType.STRING)
            if (name != null) {
                name.isMandatory = true
                name.isNotNull = true
            }
            // TODO : add description ?
            product?.createIndex("${Product::class.simpleName}_${Product::name.name}_index", OClass.INDEX_TYPE.UNIQUE, Product::type.name)
        }

        // FIXME : add SoftwareProduct ?
        var library = db.getClass(Library::class.simpleName)
        if (library == null) {
            library = db.createVertexClass(Library::class.simpleName)

            library?.addSuperClass(product)
        }

        var language = db.getClass(Language::class.simpleName)
        if (language == null) {
            language = db.createVertexClass(Language::class.simpleName)

            language?.addSuperClass(product)
        }
    }

    override fun down(db: ODatabaseSession) {
        val language = db.getClass(Language::class.simpleName)
        if (language != null) {
            db.metadata.schema.dropClass(Language::class.simpleName)
        }

        val library = db.getClass(Library::class.simpleName)
        if (library != null) {
            db.metadata.schema.dropClass(Library::class.simpleName)
        }

        val product = db.getClass(Product::class.simpleName)
        if (product != null) {
            db.metadata.schema.dropClass(Product::class.simpleName)
        }
    }

    override fun hardDown(db: ODatabaseSession) {
        val language = db.getClass(Language::class.simpleName)
        if (language != null) {
            language.truncate()
            db.metadata.schema.dropClass(Language::class.simpleName)
        }

        val library = db.getClass(Library::class.simpleName)
        if (library != null) {
            library.truncate()
            db.metadata.schema.dropClass(Library::class.simpleName)
        }

        val product = db.getClass(Product::class.simpleName)
        if (product != null) {
            product.truncate()
            db.metadata.schema.dropClass(Product::class.simpleName)
        }
    }

}