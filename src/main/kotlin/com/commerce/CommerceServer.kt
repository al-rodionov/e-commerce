package com.commerce

import com.commerce.service.TransactionStoreService
import io.grpc.Server
import io.grpc.ServerBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.annotation.PostConstruct

@Component
@Profile("server")
class CommerceServer @Autowired constructor(
    storeService: TransactionStoreService
){

    @Value("\${commerce.server.available.time}")
    lateinit var serverAvailableTime: String

    val server: Server = ServerBuilder
        .forPort(15002)
        .addService(HelloWorldService(storeService))
        .build()

    @PostConstruct
    fun start() {
        planningShutdown()

        server.start()
    }

    fun planningShutdown() {
        Runtime.getRuntime().addShutdownHook(Thread {
            server.shutdown()
            server.awaitTermination()
        })

        val executor = Executors.newSingleThreadScheduledExecutor()
        executor.schedule({
            server.shutdown()
            println("Turn down the server")
            executor.shutdown()
        }, serverAvailableTime.toLong(), TimeUnit.SECONDS)
    }

    private class HelloWorldService(
        @Autowired
        val storeService: TransactionStoreService
    ) : HelloGrpcKt.HelloCoroutineImplBase() {

        override suspend fun hello(request: HelloRequest) = helloResponse {
            storeService.store(request.message)
            message = "Hello ${request.message}"
        }
    }
}
