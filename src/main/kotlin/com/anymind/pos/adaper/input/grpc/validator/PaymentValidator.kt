package com.anymind.pos.adaper.input.grpc.validator

import com.anymind.PaymentRequest
import com.anymind.pos.domain.entity.PaymentMethod
import org.springframework.stereotype.Component

@Component
class PaymentValidator {
    fun validatePaymentGrpcRequest(request: PaymentRequest): Pair<Boolean, String?> {
        val positiveDoublePattern = Regex("^[0-9]+(\\.[0-9]+)?([eE][0-9]+)?$")
        if (!positiveDoublePattern.matches(request.price))
            return Pair(false, "Price format is invalid! (valid example : 1000.00)")

        val paymentMethod = PaymentMethod.valueOf(request.paymentMethod!!)
        if (request.priceModifier < paymentMethod.minModifier || request.priceModifier > paymentMethod.maxModifier)
            return Pair(
                false,
                "Price modifier is outside valid range for ${paymentMethod}. The valid range is between ${paymentMethod.minModifier} and ${paymentMethod.maxModifier}",
            )

        if (request.priceModifier.isNaN()) {
            return Pair(
                false,
                "Price modifier must be a valid number",
            )
        }
        return Pair(true, null)
    }
}