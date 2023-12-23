package com.example.demo.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
@Table(name = "client")
class Client {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(updatable = false)
    var id: Long? = null
    @NotBlank(message="Campo obligatorio") //validate
    var nui: String? = null
    @NotBlank(message="Campo obligatorio") //validate
    var fullname: String? = null
    @NotBlank(message="Campo obligatorio") //validate
    var address: String? = null
    @NotBlank(message="Campo obligatorio") //validate
    var email: String? = null
}