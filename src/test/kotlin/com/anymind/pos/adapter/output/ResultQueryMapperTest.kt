package com.anymind.pos.adapter.output

import com.anymind.pos.adaper.output.persistence.postgresql.mapper.ResultQueryMapper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.sql.Timestamp
import java.text.SimpleDateFormat

@SpringBootTest
class ResultQueryMapperTest {
    private val mapper = ResultQueryMapper()

    @Test
    fun listConversionTest() {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S")
        val listFromDB = listOf<Array<Any>>(
            arrayOf(Timestamp(dateFormat.parse("2022-09-01 08:00:00.0").time), 2500.00, 500),
            arrayOf(Timestamp(dateFormat.parse("2022-09-01 09:00:00.0").time), 2500.00, 500),
            arrayOf(Timestamp(dateFormat.parse("2022-09-01 10:00:00.0").time), 2500.00, 500)
        )

        val listOfSummary = mapper.mapCustomQuerySalesSummary(listFromDB)
        Assertions.assertEquals(3, listOfSummary.size)
    }
}