package com.isirode.products.api.plugins

import com.isirode.products.api.services.ProductService
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import com.isirode.products.java.schema.pojos.Product

// TODO : maybe use this https://ktor.io/docs/type-safe-routing.html
fun Application.configureRouting(productServiceParam: ProductService) {

    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        // TODO : move this into controller / resource
        route("/product") {

            get("/all") {

                call.respond(HttpStatusCode.NotImplemented)

            }

            get("/{name}") {
                val name: String? = call.parameters["name"]
                if (name == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }
                val product: Product? = productServiceParam.getOne(name)
                if (product != null) {
                    call.respond(HttpStatusCode.OK, product)
                } else {
                    call.respond(HttpStatusCode.NotFound)
                }
            }

            post("/queryMultiple") {

                // TODO : move this
                class Query (val query: String)

                val query = call.receive<Query>()

                val productList: List<Product> = productServiceParam.queryMultiple(query.query)

                // FIXME : this is duplicating properties, it is putting in the property and flatten them also
                // Need to choose between those ? Will need to check the way to query properties
                call.respond(HttpStatusCode.OK, productList)
            }

        }
    }
}
