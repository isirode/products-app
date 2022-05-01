package com.isirode.products.api

import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.jackson.*
import com.fasterxml.jackson.databind.*
import io.ktor.features.*
import io.ktor.auth.*
import io.ktor.util.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import kotlin.test.*
import io.ktor.server.testing.*

// TODO : test SQL Injection
//{
//    "query": "SELECT RID, name, status as description, 'code.product' as type FROM OUser"
//}

class ApplicationTest {
    @Test
    fun testRoot() {
        /*withTestApplication({ configureRouting() }) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("Hello World!", response.content)
            }
        }*/
    }
}