package com.anymind.pos.output

import com.anymind.pos.adaper.output.persistence.postgresql.converter.PersistenceConverter
import com.anymind.pos.adaper.output.persistence.postgresql.data.PaymentPostgre
import com.anymind.pos.domain.entity.Payment
import com.anymind.pos.domain.entity.PaymentMethod
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.time.OffsetDateTime

@SpringBootTest
class PersistenceConverterTest {
    private val converter = PersistenceConverter()

    private lateinit var paymentEntity: Payment
    private lateinit var paymentPostgre: PaymentPostgre

    @BeforeEach
    fun setup() {
        paymentEntity = Payment()
        paymentEntity.point = 30
        paymentEntity.datetime = OffsetDateTime.parse("2022-09-01T00:00:00Z")
        paymentEntity.price = 1000.00
        paymentEntity.finalPrice = 950.00
        paymentEntity.priceModifier = 0.95
        paymentEntity.paymentMethod = PaymentMethod.MASTERCARD

        paymentPostgre = PaymentPostgre()
        paymentPostgre.id = "717c7829-4b94-4af0-9514-629ec0170c39"
        paymentPostgre.point = 30
        paymentPostgre.paymentMethod = "MASTERCARD"
        paymentPostgre.price = 1000.00
        paymentPostgre.finalPrice = 950.00
        paymentPostgre.datetime = OffsetDateTime.parse("2022-09-01T00:00:00Z")
    }

    @Test
    fun convertPaymentEntityToPostgreTest() {
        val data = converter.convertPaymentEntityToPaymentPostgre(paymentEntity)

        Assertions.assertEquals(paymentPostgre.point, data.point)
        Assertions.assertEquals(paymentPostgre.paymentMethod, data.paymentMethod.toString())
        Assertions.assertEquals(paymentPostgre.price, data.price)
        Assertions.assertEquals(paymentPostgre.finalPrice, data.finalPrice)
        Assertions.assertEquals(paymentPostgre.datetime, data.datetime)
    }

    @Test
    fun convertPaymentPostgreToEntityTest() {
        val data = converter.convertPaymentPostgreToPaymentEntity(paymentPostgre)

        Assertions.assertEquals(paymentEntity.point, data.point)
        Assertions.assertEquals(paymentEntity.paymentMethod, data.paymentMethod)
        Assertions.assertEquals(paymentEntity.price, data.price)
        Assertions.assertEquals(paymentEntity.finalPrice, data.finalPrice)
        Assertions.assertEquals(paymentEntity.datetime, data.datetime)
    }
}