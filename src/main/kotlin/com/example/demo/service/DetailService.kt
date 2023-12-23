package com.example.demo.service

import com.example.demo.model.Product
import com.example.demo.model.Detail
import com.example.demo.repository.ProductRepository
import com.example.demo.repository.DetailRepository
import com.example.demo.repository.InvoiceRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class DetailService {
    @Autowired
    private lateinit var invoiceRepository: InvoiceRepository

    @Autowired
    lateinit var productRepository: ProductRepository
    @Autowired
    lateinit var detailRepository: DetailRepository
    fun list ():List<Detail>{
        return detailRepository.findAll()
    }
    @Transactional
    fun save(detail: Detail): Detail {
        try {
            productRepository.findById(detail.productId)
                ?: throw Exception("Id de la conferencia no encontrada")
            //El objeto debe estar verificado.
            detail.quantity?.toString()?.takeIf { it.trim().isNotEmpty() }
                ?: throw Exception("Cantidad no debe ser vacio")
            detail.price?.toString()?.takeIf { it.trim().isNotEmpty() }
                ?: throw Exception("Precio no debe ser vacio")

            val detailSaved = detailRepository.save(detail)

            val prod = productRepository.findById(detail.productId)
                ?: throw Exception("ID no existe")
            prod.apply {
                stock = stock?.minus(detail.quantity!!)
            }
            productRepository.save(prod)

            val listDetails = detailRepository.findByInvoiceId(detail.invoiceId)

            var sum:Double = 0.0
            listDetails.map { item ->
                sum += item.price?.times(item.quantity!!)!!
            }
            val inv = invoiceRepository.findById(detail.invoiceId)
                ?: throw Exception("ID no existe")
            inv.apply {
                total = sum.toString()
            }
            invoiceRepository.save(inv)

            return detailSaved

        }catch (ex : Exception){
            throw ResponseStatusException(
                HttpStatus.NOT_FOUND, ex.message, ex)
        }
    }
  /*  @Transactional
    fun calculateAndUpdateTotalForInvoice(invoiceId: Long) {
        val totalCalculated = detailRepository.sumTotal(invoiceId)
        val invoiceResponse = invoiceRepository.findById(invoiceId)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Factura no encontrada con ID $invoiceId") }

        invoiceResponse.total = totalCalculated
        invoiceRepository.save(invoiceResponse)
    }*/
    fun update(detail: Detail): Detail {
        try {
            detailRepository.findById(detail.id)
                ?: throw Exception("ID no existe")

            return detailRepository.save(detail)
        }
        catch (ex:Exception){
            throw ResponseStatusException(HttpStatus.NOT_FOUND,ex.message)
        }
    }
    /*fun updateName(detail: Detail): Detail {
        try{
            val response = detailRepository.findById(detail.id)
                ?: throw Exception("ID no existe")
            response.apply {
                nameassistant=assistant.nameassistant
            }
            return assistantRepository.save(response)
        }
        catch (ex:Exception){
            throw ResponseStatusException(HttpStatus.NOT_FOUND,ex.message)
        }
    }*/
    fun listById (id:Long?): Detail?{
        return detailRepository.findById(id)
    }
    fun delete (id: Long?):Boolean?{
        try{
            val response = detailRepository.findById(id)
                ?: throw Exception("ID no existe")
            detailRepository.deleteById(id!!)
            return true
        }
        catch (ex:Exception){
            throw ResponseStatusException(HttpStatus.NOT_FOUND,ex.message)
        }
    }
}