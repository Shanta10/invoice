package com.example.demo.model

import jakarta.persistence.*

@Entity
@Table(name = "detail")
class Detail {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(updatable = false)
    var id: Long? = null
    var quantity: Int? = null
    var price: Float? = null
    @Column (name="invoice_id")
    var invoiceId: Long? = null
    @Column (name="product_id")
    var productId: Long? = null
}
