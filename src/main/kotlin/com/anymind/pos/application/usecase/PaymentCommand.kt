package com.anymind.pos.application.usecase

import com.anymind.pos.domain.entity.Payment
import com.anymind.pos.domain.entity.Sales
import java.time.OffsetDateTime

interface PaymentCommand {
    fun savePayment(payment: Payment) : Payment?
    fun getBetweenStartDateAndEndDate(startDateTime: OffsetDateTime, endDateTime: OffsetDateTime): List<Sales>
}