package com.anymind.pos.domain.service

import com.anymind.pos.domain.entity.Payment
import com.anymind.pos.domain.entity.PaymentMethod
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.time.OffsetDateTime

@SpringBootTest
class PaymentServiceTest {
    private lateinit var paymentEntity: Payment

    private val service = PaymentService()

    @BeforeEach
    fun setup() {
        paymentEntity = Payment()
        paymentEntity.price = 100.00
        paymentEntity.paymentMethod = PaymentMethod.MASTERCARD
        paymentEntity.priceModifier = 0.95
        paymentEntity.datetime = OffsetDateTime.parse("2022-09-01T00:00:00Z")
    }

    @Test
    fun countFinalPriceTest() {
        val finalPrice = service.countFinalPrice(paymentEntity)
        Assertions.assertEquals(95.00, finalPrice)
    }

    @Test
    fun countPointTest() {
        val point = service.countPoint(paymentEntity)
        Assertions.assertEquals(3, point)
    }
}