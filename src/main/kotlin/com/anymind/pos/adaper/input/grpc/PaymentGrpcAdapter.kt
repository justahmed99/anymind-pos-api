package com.anymind.pos.adaper.input.grpc

import com.anymind.*
import com.anymind.pos.adaper.input.grpc.converter.PaymentConverter
import com.anymind.pos.adaper.input.grpc.validator.PaymentValidator
import com.anymind.pos.application.usecase.PaymentCommand
import com.fasterxml.jackson.databind.ObjectMapper
import io.grpc.Status
import net.devh.boot.grpc.server.service.GrpcService
import org.springframework.beans.factory.annotation.Autowired
import java.time.OffsetDateTime

@GrpcService
class PaymentGrpcAdapter(
    @Autowired private val paymentCommand: PaymentCommand,
    @Autowired private val converter: PaymentConverter,
    @Autowired private val validator: PaymentValidator
) : PaymentServiceGrpcKt.PaymentServiceCoroutineImplBase() {

    override suspend fun calculateFinalPayment(request: PaymentRequest): PaymentResponse {
        try {
            val requestValidation = validator.validatePaymentGrpcRequest(request)
            if(!requestValidation.first)
                throw IllegalArgumentException(requestValidation.second)
            val payment = paymentCommand.savePayment(converter.convertPaymentGrpcToPaymentEntity(request))
            return converter.convertPaymentEntityToPaymentResponse(payment!!)
        } catch (e: Exception) {
//            throw Status.INTERNAL.withDescription(e.message).asRuntimeException()
            val json = ObjectMapper().writeValueAsString(e.message)
            throw Status.INTERNAL.withDescription(json).asRuntimeException()
//            return PaymentResponse.newBuilder()
//                .setStatus
        }
    }

    override suspend fun listOfSalesPerHour(request: PaymentListRequest): SalesResponseList {
        try {
            val salesResponseList = paymentCommand.getBetweenStartDateAndEndDate(
                OffsetDateTime.parse(request.startDateTime), OffsetDateTime.parse(request.endDateTime)
            ).stream().map { converter.convertSalesEntityToSalesResponse(it) }.toList()
            return SalesResponseList
                .newBuilder()
                .addAllSales(salesResponseList).build()
        } catch (e: Exception) {
            throw Status.INTERNAL.withDescription(e.message).asRuntimeException()
        }

    }
}