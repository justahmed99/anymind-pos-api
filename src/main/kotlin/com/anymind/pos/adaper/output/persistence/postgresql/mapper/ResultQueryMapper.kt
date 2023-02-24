package com.anymind.pos.adaper.output.persistence.postgresql.mapper

import com.anymind.pos.adaper.output.persistence.postgresql.data.SalesSummaryPostgre
import org.springframework.stereotype.Component
import java.sql.Timestamp
//import java.time.OffsetDateTime
import java.time.ZoneOffset

@Component
class ResultQueryMapper {
    fun mapCustomQuerySalesSummary(result: List<Array<Any>>): List<SalesSummaryPostgre> {
        return result.map {
            SalesSummaryPostgre(
                (it[0] as Timestamp).toInstant().atOffset(ZoneOffset.UTC),
                (it[1] as Number).toDouble(),
                (it[2] as Number).toInt()
            )
        }
    }
}