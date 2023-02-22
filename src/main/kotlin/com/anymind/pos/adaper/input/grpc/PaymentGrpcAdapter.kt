package com.anymind.pos.adaper.input.grpc

import com.anymind.PaymentRequest
import com.anymind.PaymentResponse
import com.anymind.PaymentServiceGrpcKt
import com.anymind.SalesResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import net.devh.boot.grpc.server.service.GrpcService

@GrpcService
class PaymentGrpcAdapter: PaymentServiceGrpcKt.PaymentServiceCoroutineImplBase() {
    override suspend fun calculateFinalPayment(request: PaymentRequest): PaymentResponse {

        return PaymentResponse
            .newBuilder()
            .setFinalPrice((request.price.toDouble() * 0.95).toString())
            .setPoints((request.price.toDouble() * 0.05).toInt())
            .build()
//        return super.calculateFinalPayment(request)
    }

    override fun listOfSalesPerHour(request: PaymentRequest): Flow<SalesResponse> {
//        return super.listOfSalesPerHour(request)
        return listOf(
            SalesResponse.newBuilder().apply {
                datetime = "2022-09-01T00:00:00Z"
                sales = "1000.00"
                points = 10
            }. build(),
            SalesResponse.newBuilder().apply {
                datetime = "2022-09-01T01:00:00Z"
                sales = "2000.00"
                points = 20
            }. build(),
            SalesResponse.newBuilder().apply {
                datetime = "2022-09-01T02:00:00Z"
                sales = "5000.00"
                points = 75
            }. build()
        ).asFlow()
    }
}