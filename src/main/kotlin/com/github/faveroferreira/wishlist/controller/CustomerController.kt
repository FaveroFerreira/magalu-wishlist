package com.github.faveroferreira.wishlist.controller

import com.github.faveroferreira.wishlist.dto.CustomerDTO
import com.github.faveroferreira.wishlist.service.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
class CustomerController(
    private val customerService: CustomerService,
) {

    @GetMapping("/customer")
    fun getAllCustomers() = customerService.findAllCustomers()

    @PostMapping("/customer")
    fun createNewCustomer(@RequestBody @Valid customerDTO: CustomerDTO) =
        ResponseEntity.status(CREATED).body(customerService.createNewCustomer(customerDTO))

    @PutMapping("/customer/{customerId}")
    fun updateCustomer(@PathVariable customerId: UUID, @RequestBody @Valid customerDTO: CustomerDTO) =
        customerService.updateCustomer(customerId, customerDTO)

    @DeleteMapping("/customer/{customerId}")
    fun deleteCustomer(@PathVariable customerId: UUID) = customerService.deleteCustomer(customerId)

    @PostMapping("/customer/{customerId}/product-wishlist/{productId}")
    fun addProductToCustomerWishlist(@PathVariable customerId: UUID, @PathVariable productId: UUID) =
        customerService.addProductToWishlist(customerId, productId)

}