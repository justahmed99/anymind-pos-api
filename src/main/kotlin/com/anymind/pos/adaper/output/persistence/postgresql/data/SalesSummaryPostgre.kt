package com.anymind.pos.adaper.output.persistence.postgresql.data

import java.time.OffsetDateTime

data class SalesSummaryPostgre(
    var hour: OffsetDateTime,
    var totalPrice: Double,
    var totalPoint: Int
)
