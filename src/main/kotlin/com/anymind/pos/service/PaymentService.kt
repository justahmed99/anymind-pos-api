package com.anymind.pos.service

import com.anymind.PaymentRequest
import com.anymind.PaymentResponse
import com.anymind.PaymentServiceGrpcKt
import net.devh.boot.grpc.server.service.GrpcService

@GrpcService
class PaymentService: PaymentServiceGrpcKt.PaymentServiceCoroutineImplBase() {
    override suspend fun calculateFinalPayment(request: PaymentRequest): PaymentResponse {

        return PaymentResponse
            .newBuilder()
            .setFinalPrice((request.price.toDouble() * 0.95).toString())
            .setPoints((request.price.toDouble() * 0.05).toInt())
            .build()
//        return super.calculateFinalPayment(request)
    }
}