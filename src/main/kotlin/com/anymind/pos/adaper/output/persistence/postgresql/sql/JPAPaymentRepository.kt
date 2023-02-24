package com.anymind.pos.adaper.output.persistence.postgresql.sql

import com.anymind.pos.adaper.output.persistence.postgresql.data.PaymentPostgre
import com.anymind.pos.adaper.output.persistence.postgresql.data.SalesSummaryPostgre
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime
import java.util.*

@Repository
interface JPAPaymentRepository: JpaRepository<PaymentPostgre, String> {
    override fun findById(id: String): Optional<PaymentPostgre>

    override fun findAll(): List<PaymentPostgre>

    @Query(value = "SELECT date_trunc('hour', datetime) AS hour, " +
            "SUM(final_price) AS total_price, " +
            "SUM(point) AS total_point " +
            "FROM public.payment " +
            "WHERE datetime BETWEEN :startDate AND :endDate " +
            "GROUP BY hour", nativeQuery = true)
    fun findSalesGroupHourly(
        @Param("startDate") startDate: OffsetDateTime,
        @Param("endDate") endDate: OffsetDateTime)
    : List<Array<Any>>

}