package com.anymind.pos.adaper.output.persistence.postgresql

import com.anymind.pos.adaper.output.persistence.postgresql.converter.PersistenceConverter
import com.anymind.pos.adaper.output.persistence.postgresql.data.PaymentPostgre
import com.anymind.pos.adaper.output.persistence.postgresql.sql.JPAPaymentRepository
import com.anymind.pos.application.port.output.PaymentDatabase
import com.anymind.pos.domain.entity.Payment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.util.Date

@Repository
class PaymentPostgreAdapter : PaymentDatabase {

    @Autowired
    private val repository: JPAPaymentRepository? = null

    @Autowired
    private val converter: PersistenceConverter? = null

    override fun save(payment: Payment): Payment {
        val paymentPostgre = converter?.convertPaymentEntityToPaymentPostgre(payment)
        val paymentSave = repository?.save(paymentPostgre!!)
        return converter!!.convertPaymentPostgreToPaymentEntity(paymentSave!!)
    }

    override fun findAll(): List<Payment> {
        val paymentsPostgre = repository?.findAll()
        return paymentsPostgre?.stream()?.map { converter!!.convertPaymentPostgreToPaymentEntity(it) }?.toList() ?: emptyList()
    }

    override fun getPaymentPerDate(startDateTime: Date, endDateTime: Date): List<Payment> {
        return repository?.findBetweenStartDateAndEndate(startDateTime, endDateTime)
            ?.stream()?.map { converter!!.convertPaymentPostgreToPaymentEntity(it) }?.toList() ?: emptyList()
    }
}