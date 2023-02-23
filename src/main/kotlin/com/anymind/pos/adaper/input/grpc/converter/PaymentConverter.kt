package com.anymind.pos.adaper.input.grpc.converter

import com.anymind.PaymentRequest
import com.anymind.PaymentResponse
import com.anymind.pos.domain.entity.Payment
import com.anymind.pos.domain.entity.PaymentMethod
import org.springframework.stereotype.Component
import java.time.OffsetDateTime
import java.util.*

@Component
class PaymentConverter {

    fun convertPaymentGrpcToPaymentEntity(paymentGrpcObj: PaymentRequest): Payment {
        val paymentEntity = Payment()
        paymentEntity.price = paymentGrpcObj.price.toDouble()
        paymentEntity.priceModifier = paymentGrpcObj.priceModifier
        paymentEntity.paymentMethod = PaymentMethod.valueOf(paymentGrpcObj.paymentMethod)
        paymentEntity.datetime = OffsetDateTime.parse(paymentGrpcObj.datetime)
        return paymentEntity
    }

    fun convertPaymentEntityToPaymentResponse(paymentEntity: Payment): PaymentResponse {
        return PaymentResponse
            .newBuilder()
            .setPoints(paymentEntity.point!!)
            .setFinalPrice(paymentEntity.finalPrice.toString())
            .build()
    }
}