package com.anymind.pos.adapter.input

import com.anymind.PaymentRequest
import com.anymind.pos.adaper.input.grpc.converter.PaymentConverter
import com.anymind.pos.domain.entity.Payment
import com.anymind.pos.domain.entity.PaymentMethod
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.time.OffsetDateTime

@SpringBootTest
class PaymentConverterTest {
    private lateinit var paymentRequest: PaymentRequest
    private lateinit var paymentEntity: Payment

    private val paymentConverter = PaymentConverter()

    @BeforeEach
    fun setup() {
        paymentEntity = Payment()
        paymentEntity.price = 100.00
        paymentEntity.paymentMethod = PaymentMethod.MASTERCARD
        paymentEntity.priceModifier = 0.95
//        paymentEntity.finalPrice = 95.00
//        paymentEntity.point = 5
        paymentEntity.datetime = OffsetDateTime.parse("2022-09-01T00:00:00Z")

        paymentRequest = PaymentRequest.newBuilder()
            .setDatetime("2022-09-01T00:00:00Z")
            .setPaymentMethod("MASTERCARD")
            .setPriceModifier(0.95)
            .setPrice("100.00")
            .build()
    }

    @Test
    fun testConverterRequestToEntity() {
        val convertRequestToEntity = paymentConverter.convertPaymentGrpcToPaymentEntity(paymentRequest)

        Assertions.assertEquals(paymentEntity.price, convertRequestToEntity.price)
        Assertions.assertEquals(paymentEntity.paymentMethod, convertRequestToEntity.paymentMethod)
        Assertions.assertEquals(paymentEntity.priceModifier, convertRequestToEntity.priceModifier)
        Assertions.assertEquals(paymentEntity.datetime, convertRequestToEntity.datetime)
    }

}