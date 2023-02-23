package com.anymind.pos.domain.entity

import java.time.OffsetDateTime

class Payment {
    var id: String? = null
    var price: Double? = null
    var finalPrice: Double? = null
    var priceModifier: Double? = null
    var paymentMethod: PaymentMethod? = null
    var point: Int? = null
    var datetime: OffsetDateTime? = null
}