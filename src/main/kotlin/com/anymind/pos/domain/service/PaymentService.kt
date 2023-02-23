package com.anymind.pos.domain.service

import com.anymind.pos.domain.entity.Payment
import org.springframework.stereotype.Service

@Service
class PaymentService {
    fun countFinalPrice(payment: Payment): Double {
        return payment.price!!.toDouble() * payment.priceModifier!!
    }

    fun countPoint(payment: Payment): Int {
        return (payment.price!!.toDouble() * payment.paymentMethod?.pointsMultiplier!!).toInt()
    }
}