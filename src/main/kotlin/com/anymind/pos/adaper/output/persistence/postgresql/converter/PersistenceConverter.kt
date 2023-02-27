package com.anymind.pos.adaper.output.persistence.postgresql.converter

import com.anymind.pos.adaper.output.persistence.postgresql.data.PaymentPostgre
import com.anymind.pos.adaper.output.persistence.postgresql.data.SalesSummaryPostgre
import com.anymind.pos.domain.entity.Payment
import com.anymind.pos.domain.entity.PaymentMethod
import com.anymind.pos.domain.entity.Sales
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class PersistenceConverter {
    fun convertPaymentEntityToPaymentPostgre(paymentEntity: Payment): PaymentPostgre {
        val paymentPostgre = PaymentPostgre()
        paymentPostgre.id = paymentEntity.id
        paymentPostgre.price = paymentEntity.price
        paymentPostgre.finalPrice = paymentEntity.finalPrice
        paymentPostgre.priceModifier = paymentEntity.priceModifier
        paymentPostgre.point = paymentEntity.point
        paymentPostgre.datetime = paymentEntity.datetime
        paymentPostgre.paymentMethod = paymentEntity.paymentMethod?.name

        return paymentPostgre
    }

    fun convertPaymentPostgreToPaymentEntity(paymentPostgre: PaymentPostgre): Payment {
        val paymentEntity = Payment()
        paymentEntity.id = paymentPostgre.id
        paymentEntity.price = paymentPostgre.price
        paymentEntity.finalPrice = paymentPostgre.finalPrice
        paymentEntity.point = paymentPostgre.point
        paymentEntity.datetime = paymentPostgre.datetime
        paymentEntity.priceModifier = paymentPostgre.priceModifier
        paymentEntity.paymentMethod = PaymentMethod.valueOf(paymentPostgre.paymentMethod!!)

        return paymentEntity
    }

    fun convertSalesSummaryPostgreToSalesEntity(salesSummaryPostgre: SalesSummaryPostgre): Sales {
        val sales = Sales()
        sales.datetime = salesSummaryPostgre.hour
        sales.sales = salesSummaryPostgre.totalPrice
        sales.points = salesSummaryPostgre.totalPoint
        return sales
    }
}