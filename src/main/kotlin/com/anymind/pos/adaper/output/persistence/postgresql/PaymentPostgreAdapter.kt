package com.anymind.pos.adaper.output.persistence.postgresql

import com.anymind.pos.adaper.output.persistence.postgresql.converter.PersistenceConverter
import com.anymind.pos.adaper.output.persistence.postgresql.mapper.ResultQueryMapper
import com.anymind.pos.adaper.output.persistence.postgresql.sql.JPAPaymentRepository
import com.anymind.pos.application.port.output.PaymentDatabase
import com.anymind.pos.domain.entity.Payment
import com.anymind.pos.domain.entity.Sales
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime

@Repository
class PaymentPostgreAdapter(
    @Autowired private val repository: JPAPaymentRepository,
    @Autowired private val converter: PersistenceConverter,
    @Autowired private val mapper: ResultQueryMapper
) : PaymentDatabase {

    override fun save(payment: Payment): Payment? {
        val paymentPostgre = converter.convertPaymentEntityToPaymentPostgre(payment)
        val paymentSave = repository.save(paymentPostgre)
        return converter.convertPaymentPostgreToPaymentEntity(paymentSave)
    }

    override fun getPaymentPerHour(startDateTime: OffsetDateTime, endDateTime: OffsetDateTime): List<Sales> {
        return mapper.mapCustomQuerySalesSummary(repository.findSalesGroupHourly(startDateTime, endDateTime))
            .stream()
            .map { converter.convertSalesSummaryPostgreToSalesEntity(it) }.toList()
    }
}