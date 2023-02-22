package com.anymind.pos.domain.service

import com.anymind.pos.domain.entity.Payment
import org.springframework.stereotype.Service

@Service
class PaymentService {
    fun countFinalPrice(payment: Payment): String {
        return (payment.price!!.toDouble() * payment.priceModifier!!).toString()
    }


}