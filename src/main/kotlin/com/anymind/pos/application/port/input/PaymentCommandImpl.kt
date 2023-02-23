package com.anymind.pos.application.port.input

import com.anymind.pos.application.port.output.PaymentDatabase
import com.anymind.pos.application.usecase.PaymentCommand
import com.anymind.pos.domain.entity.Payment
import com.anymind.pos.domain.service.PaymentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
@Service
class PaymentCommandImpl:PaymentCommand {
    @Autowired
    private val database: PaymentDatabase? = null

    @Autowired
    private val service: PaymentService? = null

    override fun savePayment(payment: Payment): Payment? {
        payment.finalPrice = service?.countFinalPrice(payment)
        payment.point = service?.countPoint(payment)
        return database?.save(payment)
    }

    override fun getBetweenStartDateAndEndDate(startDateTime: Date, endDateTime: Date): List<Payment> {
        return database!!.getPaymentPerDate(startDateTime, endDateTime)
    }
}