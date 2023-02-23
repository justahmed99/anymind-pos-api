package com.anymind.pos.application.port.output

import com.anymind.pos.domain.entity.Payment
import java.util.Date

interface PaymentDatabase {
    fun save(payment: Payment): Payment
    fun findAll(): List<Payment>
    fun getPaymentPerDate(startDateTime: Date, endDateTime: Date): List<Payment>
}