package com.example.demo.controller

import com.example.demo.model.Invoice
import com.example.demo.model.InvoiceView
import com.example.demo.service.InvoiceService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/invoice")   //endpoint

class InvoiceController {
    @Autowired
    lateinit var invoiceService: InvoiceService

    @GetMapping
    fun list ():List <Invoice>{
        return invoiceService.list()
    }
    @GetMapping("/view")
    fun listview ():List <InvoiceView>{
        return invoiceService.listview()
    }
    @PostMapping
    fun save (@RequestBody @Valid invoice: Invoice):ResponseEntity<Invoice>{
        return ResponseEntity(invoiceService.save(invoice), HttpStatus.OK)
    }
    @PutMapping
    fun update (@RequestBody invoice: Invoice):ResponseEntity<Invoice>{
        return ResponseEntity(invoiceService.update(invoice), HttpStatus.OK)
    }
    @PatchMapping
    fun updateName (@RequestBody invoice: Invoice):ResponseEntity<Invoice>{
        return ResponseEntity(invoiceService.updateName(invoice), HttpStatus.OK)
    }
    @GetMapping("/{id}")
    fun listById (@PathVariable("id") id: Long): ResponseEntity<*>{
        return ResponseEntity(invoiceService.listById (id), HttpStatus.OK)
    }
    @DeleteMapping("/delete/{id}")
    fun delete (@PathVariable("id") id: Long):Boolean?{
        return invoiceService.delete(id)
    }
}