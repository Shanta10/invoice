package com.example.demo.repository

import com.example.demo.model.Detail
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface DetailRepository : JpaRepository<Detail, Long?> {

    fun findById (id: Long?): Detail?

    fun findByInvoiceId (invoiceId: Long?): List <Detail>

   // @Query("SELECT SUM(d.price * d.quantity) FROM Detail d WHERE d.invoiceId = :invoiceId")
    //fun sumTotal(invoiceId: Long): Int?
}