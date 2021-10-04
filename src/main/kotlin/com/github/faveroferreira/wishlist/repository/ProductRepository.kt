package com.github.faveroferreira.wishlist.repository

import com.github.faveroferreira.wishlist.model.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ProductRepository: JpaRepository<Product, UUID>
