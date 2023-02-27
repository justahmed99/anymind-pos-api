package com.anymind.pos.adaper.output.persistence.postgresql.data

import java.time.OffsetDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "payment", schema = "public")
class PaymentPostgre {
    @Id
    @Column(name = "id", unique = true)
    var id: String? = null
    var price: Double? = null
    var finalPrice: Double? = null
    var paymentMethod: String? = null
    var priceModifier: Double? = null
    var point: Int? = null
    var datetime: OffsetDateTime? = null
}