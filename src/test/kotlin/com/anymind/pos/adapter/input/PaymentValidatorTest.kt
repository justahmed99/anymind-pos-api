package com.anymind.pos.adapter.input

import com.anymind.PaymentRequest
import com.anymind.pos.adaper.input.grpc.validator.PaymentValidator
import com.anymind.pos.domain.entity.PaymentMethod
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class PaymentValidatorTest {
    private lateinit var paymentRequest: PaymentRequest
    private val validator = PaymentValidator()

    @BeforeEach
    fun setup() {
        paymentRequest = PaymentRequest.newBuilder()
            .setDatetime("2022-09-01T00:00:00Z")
            .setPaymentMethod("MASTERCARD")
            .setPriceModifier(0.95)
            .setPrice("100.00")
            .build()
    }

    @Test
    fun allCorrectTest() {
        val validation = validator.validatePaymentGrpcRequest(paymentRequest)
        Assertions.assertEquals(true, validation.first)
        Assertions.assertEquals(null, validation.second)
    }

    @Test
    fun invalidDateTimeTest() {
        val modifiedRequest = paymentRequest.toBuilder().setDatetime("2022-09-01T00:00:00Zabc").build()
        val validation = validator.validatePaymentGrpcRequest(modifiedRequest)
        Assertions.assertEquals(false, validation.first)
        Assertions.assertEquals("Invalid date format. The format should be yyyy-MM-ddThh:mm:ssZ", validation.second)
    }

    @Test
    fun invalidPriceModifierRangeTest() {
        val modifiedRequest = paymentRequest.toBuilder().setPriceModifier(2.0).build()
        val paymentMethod = PaymentMethod.valueOf(modifiedRequest.paymentMethod!!)
        val validation = validator.validatePaymentGrpcRequest(modifiedRequest)
        Assertions.assertEquals(false, validation.first)
        Assertions.assertEquals("Price modifier is outside valid range for ${paymentMethod}. The valid range is between ${paymentMethod.minModifier} and ${paymentMethod.maxModifier}", validation.second)
    }

    @Test
    fun invalidPriceTest() {
        val modifiedRequest = paymentRequest.toBuilder().setPrice("abc.def").build()
        val validation = validator.validatePaymentGrpcRequest(modifiedRequest)
        Assertions.assertEquals(false, validation.first)
        Assertions.assertEquals("Price format is invalid! (valid example : 1000.00)", validation.second)
    }
}