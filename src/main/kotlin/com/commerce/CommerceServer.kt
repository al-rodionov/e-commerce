package com.commerce

import com.commerce.grpc.*
import com.commerce.mapper.toContainer
import com.commerce.service.PointsCalcService
import com.commerce.service.PriceCalcService
import com.commerce.service.TransactionStoreService
import com.commerce.service.ValidatorService
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
    validatorService: ValidatorService,
    storeService: TransactionStoreService,
    priceCalcService: PriceCalcService,
    pointsCalcService: PointsCalcService,
){

    @Value("\${commerce.server.available.time}")
    lateinit var serverAvailableTime: String

    val server: Server = ServerBuilder
        .forPort(15002)
        .addService(TransactionGrpcService(validatorService,
            storeService, priceCalcService, pointsCalcService))
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

    private class TransactionGrpcService @Autowired constructor(
        val validatorService: ValidatorService,
        val storeService: TransactionStoreService,
        val priceCalcService: PriceCalcService,
        val pointsCalcService: PointsCalcService
    ) : CommerceGrpcKt.CommerceCoroutineImplBase() {

        override suspend fun transaction(request: TransactionRequest) = transactionResponse {
            val container = toContainer(request)

            validatorService.validate(container)

            storeService.store(container)

//            val finalPrice = priceCalc.calculate(container)
//            val points = pointsCalc.calculate(container)

//            mapFromGrpc-contaainer
//            validate - need list1
//            store
//            calculate response - need list1

            finalPrice = priceCalcService.calculate(container)
            points = pointsCalcService.calculate(container)
        }

        override suspend fun transactionReport(request: TransactionReportRequest): TransactionReportResponse {
            return super.transactionReport(request)
        }
    }
}
