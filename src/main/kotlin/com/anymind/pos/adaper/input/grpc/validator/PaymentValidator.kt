package com.anymind.pos.adaper.input.grpc.validator

import com.anymind.PaymentRequest
import com.anymind.pos.domain.entity.PaymentMethod
import org.springframework.stereotype.Component
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@Component
class PaymentValidator {
    fun validatePaymentGrpcRequest(request: PaymentRequest): Pair<Boolean, String?> {
        // validate price
        val positiveDoublePattern = Regex("^[0-9]+(\\.[0-9]+)?([eE][0-9]+)?$")
        if (!positiveDoublePattern.matches(request.price))
            return Pair(false, "Price format is invalid! (valid example : 1000.00)")

        // validate payment method
        val paymentMethod = PaymentMethod.valueOf(request.paymentMethod!!)
        if (request.priceModifier < paymentMethod.minModifier || request.priceModifier > paymentMethod.maxModifier)
            return Pair(
                false,
                "Price modifier is outside valid range for ${paymentMethod}. The valid range is between ${paymentMethod.minModifier} and ${paymentMethod.maxModifier}",
            )

        // validate price modifier
        if (request.priceModifier.isNaN()) {
            return Pair(
                false,
                "Price modifier must be a valid number",
            )
        }

        // validate datetime
        if (!validateDateString(request.datetime))
            return Pair(
                false,
                "Invalid date format. The format should be yyyy-MM-ddThh:mm:ssZ",
            )

        return Pair(true, null)
    }

    fun validateDateString(dateString: String): Boolean {
        val formatter = DateTimeFormatter.ISO_DATE_TIME
        try {
            formatter.parse(dateString)
            return true
        } catch (e: DateTimeParseException) {
            return false
        }
    }
}