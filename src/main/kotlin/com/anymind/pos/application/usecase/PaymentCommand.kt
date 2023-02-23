package com.anymind.pos.application.usecase

import com.anymind.pos.domain.entity.Payment
import java.util.*

interface PaymentCommand {
    fun savePayment(payment: Payment) : Payment?
    fun getBetweenStartDateAndEndDate(startDateTime: Date, endDateTime: Date): List<Payment>
}