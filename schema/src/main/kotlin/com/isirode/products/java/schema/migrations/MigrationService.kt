package com.isirode.products.java.schema.migrations

import com.isirode.products.java.schema.migrations.orientdb.InitialMigration_0_0_0
import com.orientechnologies.orient.core.db.ODatabaseSession

// TODO (feature) : create if necessary a table reference, insert the version, up to version, if we up
class MigrationService {

    val migrations: List<Migration> = listOf(
        InitialMigration_0_0_0()
    )
    
    fun up(db: ODatabaseSession) {
        migrations.forEach { it.up(db) }
    }

    fun down(db: ODatabaseSession) {
        migrations.reversed().forEach { it.down(db) }
    }

    fun hardDown(db: ODatabaseSession) {
        migrations.reversed().forEach { it.hardDown(db) }
    }
    
}