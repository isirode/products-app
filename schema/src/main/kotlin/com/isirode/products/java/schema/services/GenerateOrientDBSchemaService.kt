package com.isirode.products.java.schema.services

import com.isirode.products.java.schema.migrations.MigrationDirection
import com.isirode.products.java.schema.migrations.MigrationService
import com.orientechnologies.orient.core.db.ODatabaseType
import com.orientechnologies.orient.core.db.OrientDB
import com.orientechnologies.orient.core.db.OrientDBConfig

class GenerateOrientDBSchemaService {

    fun execute(serverUrl: String, databaseName: String, serverUsername: String,
                serverPassword: String, readerUsername: String, readerPassword: String, direction: MigrationDirection) {

        println("Execute ${GenerateOrientDBSchemaService::class.simpleName}::${this::execute.name}")

        OrientDB(serverUrl, serverUsername, serverPassword, OrientDBConfig.defaultConfig()).use { orientDB ->
            if (!orientDB.createIfNotExists(databaseName, ODatabaseType.PLOCAL)) {
                // nothing
            }

            // INFO : users admin, reader, writer created by default https://orientdb.org/docs/3.0.x/fiveminute/java-3.html#connecting-to-the-db
            orientDB.open(databaseName, serverUsername, serverPassword).use {
                val migrationService = MigrationService()

                when(direction) {
                    MigrationDirection.UP -> migrationService.up(it)
                    MigrationDirection.DOWN -> migrationService.down(it)
                    MigrationDirection.HARDDOWN -> migrationService.hardDown(it)
                }

                // TODO : put it in the migration
                it.execute(
                    "sql",
                    "INSERT INTO OUser SET name = ?, password = ?, status = 'ACTIVE', roles = (SELECT FROM ORole WHERE name = 'reader')",
                    readerUsername, readerPassword
                ).close()

            }
        }
    }

}