package com.anymind.pos.application.port.output

import com.anymind.pos.domain.entity.Payment
import com.anymind.pos.domain.entity.Sales
import java.time.OffsetDateTime

interface PaymentDatabase {
    fun save(payment: Payment): Payment?
    fun getPaymentPerHour(startDateTime: OffsetDateTime, endDateTime: OffsetDateTime): List<Sales>
}