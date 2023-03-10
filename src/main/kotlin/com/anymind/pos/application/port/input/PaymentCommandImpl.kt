package com.anymind.pos.application.port.input

import com.anymind.pos.application.port.output.PaymentDatabase
import com.anymind.pos.application.usecase.PaymentCommand
import com.anymind.pos.domain.entity.Payment
import com.anymind.pos.domain.entity.Sales
import com.anymind.pos.domain.service.PaymentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.util.*

@Service
class PaymentCommandImpl(
    @Autowired private val database: PaymentDatabase,
    @Autowired private val service: PaymentService
) : PaymentCommand {

    override fun savePayment(payment: Payment): Payment? {
        payment.finalPrice = service.countFinalPrice(payment)
        payment.point = service.countPoint(payment)
        payment.id = UUID.randomUUID().toString()
        return database.save(payment)
    }

    override fun getBetweenStartDateAndEndDate(startDateTime: OffsetDateTime, endDateTime: OffsetDateTime): List<Sales> {
        return database.getPaymentPerHour(startDateTime, endDateTime)
    }
}