package com.anymind.pos.adapter.output

import com.anymind.pos.adaper.output.persistence.postgresql.PaymentPostgreAdapter
import com.anymind.pos.adaper.output.persistence.postgresql.data.PaymentPostgre
import com.anymind.pos.adaper.output.persistence.postgresql.sql.JPAPaymentRepository
import com.anymind.pos.domain.entity.Payment
import com.anymind.pos.domain.entity.PaymentMethod
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.OffsetDateTime
import java.util.UUID

@SpringBootTest
class PaymentPostgreAdapterTest {
    @Autowired
    private lateinit var adapter: PaymentPostgreAdapter

    @Test
    fun testSave() {
        val paymentPostgre = PaymentPostgre()
        paymentPostgre.id = UUID.randomUUID().toString()
        paymentPostgre.price = 1000.00
        paymentPostgre.finalPrice = 950.00
        paymentPostgre.paymentMethod = PaymentMethod.MASTERCARD.name
        paymentPostgre.point = 30
        paymentPostgre.priceModifier = 0.95
        paymentPostgre.datetime = OffsetDateTime.parse("2022-09-01T00:00:00Z")

        val payment = Payment()
        payment.id = paymentPostgre.id
        payment.price = 1000.00
        payment.finalPrice = 950.00
        payment.paymentMethod = PaymentMethod.MASTERCARD
        payment.point = 30
        payment.priceModifier = 0.95
        payment.datetime = OffsetDateTime.parse("2022-09-01T00:00:00Z")

        val repoMock = mock(JPAPaymentRepository::class.java)
        `when`(repoMock.save(paymentPostgre)).thenReturn(paymentPostgre)

        val saveResult = adapter.save(payment)

        Assertions.assertEquals(payment.id, saveResult.id)
        Assertions.assertEquals(payment.paymentMethod, saveResult.paymentMethod)
        Assertions.assertEquals(payment.price, saveResult.price)
        Assertions.assertEquals(payment.datetime, saveResult.datetime)
        Assertions.assertEquals(payment.finalPrice, saveResult.finalPrice)
        Assertions.assertEquals(payment.point, saveResult.point)
    }

}