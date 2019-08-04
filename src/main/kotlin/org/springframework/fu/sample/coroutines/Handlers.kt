package org.springframework.fu.sample.coroutines

import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.bodyAndAwait
import org.springframework.web.reactive.function.server.renderAndAwait

class UserHandler(
        private val repository: UserRepository,
        private val configuration: SampleProperties
) {

    suspend fun listApi(request: ServerRequest) =
            ok().contentType(MediaType.APPLICATION_JSON).bodyAndAwait(repository.findAll())

	suspend fun findUserById(request: ServerRequest) =
			ok().contentType(MediaType.APPLICATION_JSON).bodyAndAwait(repository.findOne(request.pathVariable("id").toInt()))

    suspend fun addUser(request: ServerRequest): ServerResponse {
        println(request)
       val user = User(id = request.pathVariable("id").toInt(),
                       login = request.pathVariable("login"),
                       firstname = request.pathVariable("firstname"),
                       lastname = request.pathVariable("lastname"))
        println(user)
       return ok().contentType(MediaType.APPLICATION_JSON).bodyAndAwait(repository.insert(user))
    }

    suspend fun listView(request: ServerRequest) =
			ok().renderAndAwait("users", mapOf("users" to repository.findAll()))


    suspend fun conf(request: ServerRequest) =
            ok().bodyAndAwait(configuration)

}