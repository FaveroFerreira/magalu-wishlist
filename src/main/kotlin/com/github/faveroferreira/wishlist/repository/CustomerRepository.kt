package com.github.faveroferreira.wishlist.repository

import com.github.faveroferreira.wishlist.model.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CustomerRepository : JpaRepository<Customer, UUID> {

    fun findByEmail(email: String): Customer?

}
