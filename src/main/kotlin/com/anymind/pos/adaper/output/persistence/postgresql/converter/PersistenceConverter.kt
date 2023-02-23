package com.anymind.pos.adaper.output.persistence.postgresql.converter

import com.anymind.pos.adaper.output.persistence.postgresql.data.PaymentPostgre
import com.anymind.pos.domain.entity.Payment
import com.anymind.pos.domain.entity.PaymentMethod
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class PersistenceConverter {
    fun convertPaymentEntityToPaymentPostgre(paymentEntity: Payment): PaymentPostgre {
        val paymentPostgre = PaymentPostgre()
        paymentPostgre.id = UUID.randomUUID().toString()
        paymentPostgre.price = paymentEntity.price
        paymentPostgre.finalPrice = paymentEntity.finalPrice
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
        paymentEntity.paymentMethod = PaymentMethod.valueOf(paymentPostgre.paymentMethod!!)

        return paymentEntity
    }
}