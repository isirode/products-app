package com.isirode.products.java.schema.migrations

import com.orientechnologies.orient.core.db.ODatabaseSession

// FIXME : rename it so that it can share interface with the service
interface Migration {
    fun up(db: ODatabaseSession)
    /**
     * Drop the schema but keep the data
     */
    fun down(db: ODatabaseSession)
    /**
     * Drop the data along with the schema
     */
    fun hardDown(db: ODatabaseSession)
}