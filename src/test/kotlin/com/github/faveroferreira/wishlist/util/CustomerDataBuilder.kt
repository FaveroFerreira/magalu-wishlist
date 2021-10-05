package com.github.faveroferreira.wishlist.util

import com.github.faveroferreira.wishlist.dto.CustomerDTO
import com.github.faveroferreira.wishlist.model.Customer
import java.util.*

data class CustomerDataBuilder(
    val id: UUID = UUID.randomUUID(),
    val name: String = "Juan Junior Neto",
    val email: String = "Juan Junior Neto",
) {
    fun buildDTO() = CustomerDTO(name, email)

    fun buildModel() = Customer(id, name, email)
}