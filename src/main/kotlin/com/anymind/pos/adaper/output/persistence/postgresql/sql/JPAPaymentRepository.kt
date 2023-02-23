package com.anymind.pos.adaper.output.persistence.postgresql.sql

import com.anymind.pos.adaper.output.persistence.postgresql.data.PaymentPostgre
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface JPAPaymentRepository: JpaRepository<PaymentPostgre, String> {
    override fun findById(id: String): Optional<PaymentPostgre>

    override fun findAll(): List<PaymentPostgre>

    @Query("SELECT p FROM PaymentPostgre p WHERE p.datetime " +
            "BETWEEN :startDate AND :endDate ")
    fun findBetweenStartDateAndEndate(
        @Param("startDate") startDate: Date,
        @Param("endDate") endDate: Date): List<PaymentPostgre>

}