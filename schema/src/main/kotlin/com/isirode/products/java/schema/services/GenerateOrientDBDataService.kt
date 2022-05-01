package com.isirode.products.java.schema.services

import com.isirode.products.java.schema.orm.orientdb.OrmService
import com.isirode.products.java.schema.serde.deserializer.ProductTomlDeserializer
import com.orientechnologies.orient.core.db.OrientDB
import com.orientechnologies.orient.core.db.OrientDBConfig
import java.io.File

// TODO : add a way to filter folder / file (WIP system)
// TODO : use log4j2
class GenerateOrientDBDataService {

    fun execute(serverUrl: String, databaseName: String, serverUsername: String, serverPassword: String, folderPath: String) {

        println("Execute ${GenerateOrientDBDataService::class.simpleName}::${this::execute.name}")

        OrientDB(serverUrl, serverUsername, serverPassword, OrientDBConfig.defaultConfig()).use { orientDB ->
            // INFO : users admin, reader, writer are supposed to be created by default https://orientdb.org/docs/3.2.x/fiveminute/java-3.html#connecting-to-the-db
            // But they are not, roles are present though
            orientDB.open(databaseName, serverUsername, serverPassword).use { session ->
                val ormService = OrmService(session)

                val productJsonDeserializer = ProductTomlDeserializer.getDefaultTomlDeserializer()

                File(folderPath).walkTopDown().forEach {

                    if (!it.path.endsWith(".toml")) return@forEach

                    println("Starting handling file $it")

                    try {
                        val fileContent = File(it.path).readText(Charsets.UTF_8)
                        val product = productJsonDeserializer.deserialize(fileContent)
                        ormService.persist(product)
                    } catch (e: RuntimeException) {
                        // TODO : replace by log4j
                        println(e)
                    }

                    println("Content of file $it was inserted in database")
                }
            }
        }
    }
}