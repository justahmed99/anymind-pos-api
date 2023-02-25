package com.anymind.pos.adaper.input.grpc.converter

import com.anymind.PaymentRequest
import com.anymind.PaymentResponse
import com.anymind.SalesResponse
import com.anymind.pos.domain.entity.Payment
import com.anymind.pos.domain.entity.PaymentMethod
import com.anymind.pos.domain.entity.Sales
import org.springframework.stereotype.Component
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

@Component
class PaymentConverter {

    fun convertPaymentGrpcToPaymentEntity(paymentGrpcObj: PaymentRequest): Payment {
        val paymentEntity = Payment()
        paymentEntity.price = paymentGrpcObj.price.toDouble()
        paymentEntity.paymentMethod = PaymentMethod.valueOf(paymentGrpcObj.paymentMethod)
        paymentEntity.priceModifier = paymentGrpcObj.priceModifier
        paymentEntity.datetime = OffsetDateTime.parse(
            paymentGrpcObj.datetime,
            DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneOffset.UTC)
        )
        return paymentEntity
    }

    fun convertPaymentEntityToPaymentResponse(paymentEntity: Payment): PaymentResponse {
        return PaymentResponse
            .newBuilder()
            .setPoints(paymentEntity.point!!)
            .setFinalPrice(paymentEntity.finalPrice.toString())
            .build()
    }

    fun convertSalesEntityToSalesResponse(salesEntity: Sales): SalesResponse {
        return SalesResponse
            .newBuilder()
            .setDatetime(salesEntity.datetime.toString())
            .setSales(salesEntity.sales.toString())
            .setPoints(salesEntity.points!!)
            .build()
    }
}