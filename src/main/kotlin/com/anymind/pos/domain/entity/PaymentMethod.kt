package com.anymind.pos.domain.entity

enum class PaymentMethod (val minModifier: Double, val maxModifier: Double, val pointsMultiplier: Double ) {
    CASH(0.9, 1.0, 0.05),
    CASH_ON_DELIVERY(1.0, 1.02, 0.05),
    VISA(0.95, 1.0, 0.03),
    MASTERCARD(0.95, 1.0, 0.03),
    AMEX(0.98, 1.01, 0.02),
    JCB(0.95, 1.0, 0.05)
}