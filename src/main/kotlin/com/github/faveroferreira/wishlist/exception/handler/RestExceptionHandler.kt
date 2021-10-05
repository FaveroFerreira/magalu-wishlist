package com.github.faveroferreira.wishlist.exception.handler

import com.github.faveroferreira.wishlist.dto.ErrorDetailsDTO
import com.github.faveroferreira.wishlist.exception.CustomerEmailAlreadyTakenException
import com.github.faveroferreira.wishlist.exception.CustomerNotFoundException
import com.github.faveroferreira.wishlist.exception.DuplicateWishlistProductException
import com.github.faveroferreira.wishlist.exception.ProductNotFoundException
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.badRequest
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class RestExceptionHandler {

    @ExceptionHandler(CustomerEmailAlreadyTakenException::class)
    fun handleCustomerEmailAlreadyTakenException(ex: CustomerEmailAlreadyTakenException): ResponseEntity<Any> {
        return badRequest().body(ErrorDetailsDTO("Email already taken!"))
    }

    @ExceptionHandler(CustomerNotFoundException::class)
    fun handleCustomerNotFoundException(ex: CustomerNotFoundException): ResponseEntity<Any> {
        return badRequest().body(ErrorDetailsDTO("Customer not found!"))
    }

    @ExceptionHandler(ProductNotFoundException::class)
    fun handleProductNotFoundException(ex: ProductNotFoundException): ResponseEntity<Any> {
        return badRequest().body(ErrorDetailsDTO("Product not found!"))
    }

    @ExceptionHandler(DuplicateWishlistProductException::class)
    fun handleDuplicateWishlistProductException(ex: DuplicateWishlistProductException): ResponseEntity<Any> {
        return badRequest().body(ErrorDetailsDTO("Product is already in your wishlist!"))
    }

}