package com.isirode.products.java.schema.orm.orientdb.integration

import com.isirode.products.java.schema.migrations.MigrationService
import com.isirode.products.java.schema.orm.orientdb.OrmService
import com.orientechnologies.orient.core.db.ODatabaseSession
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import org.testcontainers.containers.OrientDBContainer
import org.testcontainers.containers.wait.strategy.LogMessageWaitStrategy
import org.testcontainers.utility.DockerImageName
import com.isirode.products.java.schema.pojos.Product
import com.isirode.products.java.schema.pojos.software.Library

class OrmServiceTest {

    var orientDBContainer: OrientDBContainer? = null

    //@Container
    @BeforeEach
    fun init() {
        orientDBContainer = OrientDBContainer(DockerImageName.parse("orientdb").withTag("3.2.5"))
            .withServerPassword("admin")
        //.withDatabaseName("products")
        //.withServerPassword("secret")
        orientDBContainer!!.setWaitStrategy(LogMessageWaitStrategy().withRegEx(".*OrientDB Studio available at.*"))//.withRegEx(".*OrientDB Server is active.*"))
        orientDBContainer!!.start()

    }

    @Test
    fun toto() {
        if (orientDBContainer == null) fail("orientdb not ready")

        // newer version of OrientDB do not seem to use admin:admin
        val session: ODatabaseSession = orientDBContainer!!.getSession("root", "admin")

        session.command("CREATE CLASS Person EXTENDS V");
        session.command("INSERT INTO Person set name='john'");
        session.command("INSERT INTO Person set name='jane'");

        assert(session.query("SELECT FROM Person").stream().count() == 2L)

    }

    @Test
    fun shouldBeAbleToSaveLibraryAndFetchIt() {
        // given
        val db: ODatabaseSession = orientDBContainer!!.getSession("root", "admin")
        val migrationService = MigrationService()
        val ormService = OrmService(db)

        migrationService.up(db)

        val expectedLibrary = Library()
        expectedLibrary.name = "test"
        expectedLibrary.type = "software.library"
        expectedLibrary.writtenFor = "test"
        expectedLibrary.writtenIn = "test"
        expectedLibrary.properties = hashMapOf("test" to "test") as Map<String, Any>?

        // when
        ormService.persist(expectedLibrary)

        assert(db.query("SELECT FROM Library").stream().count() == 1L)

        val library: Product? = ormService.getOne(expectedLibrary.name)

        // then
        assert(library != null)
        assert(library is Library)
        assertThat(library).isEqualTo(expectedLibrary)
    }

    // TODO : test without type, no data lost etc

}