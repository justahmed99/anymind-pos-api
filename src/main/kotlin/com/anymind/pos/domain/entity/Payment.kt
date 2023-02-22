package com.anymind.pos.domain.entity

import java.util.Date

class Payment {
    var price: Double? = null
    var priceModifier: Double? = null
    var paymentMethod: String? = null
    var points: Int? = null
    var datetime: Date? = null
}