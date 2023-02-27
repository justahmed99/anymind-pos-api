package com.anymind.pos.adapter.output

import com.anymind.pos.adaper.output.persistence.postgresql.converter.PersistenceConverter
import com.anymind.pos.adaper.output.persistence.postgresql.data.PaymentPostgre
import com.anymind.pos.adaper.output.persistence.postgresql.data.SalesSummaryPostgre
import com.anymind.pos.domain.entity.Payment
import com.anymind.pos.domain.entity.PaymentMethod
import com.anymind.pos.domain.entity.Sales
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.event.annotation.BeforeTestMethod
import java.time.OffsetDateTime

@SpringBootTest
class PersistenceConverterTest {
    private val converter = PersistenceConverter()

    private lateinit var paymentEntity: Payment
    private lateinit var paymentPostgre: PaymentPostgre

    private lateinit var sales: Sales
    private lateinit var salesSummaryPostgre: SalesSummaryPostgre

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

        salesSummaryPostgre = SalesSummaryPostgre(
            OffsetDateTime.parse("2022-09-01T00:00:00Z"),
            1000.00,
            20
        )

        sales = Sales()
        sales.datetime = OffsetDateTime.parse("2022-09-01T00:00:00Z")
        sales.sales = 1000.00
        sales.points = 20
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

    @Test
    fun convertSalesSummaryPostgreToEntity() {
        val data = converter.convertSalesSummaryPostgreToSalesEntity(salesSummaryPostgre)

        Assertions.assertEquals(sales.datetime, data.datetime)
        Assertions.assertEquals(sales.sales, data.sales)
        Assertions.assertEquals(sales.points, data.points)
    }
}