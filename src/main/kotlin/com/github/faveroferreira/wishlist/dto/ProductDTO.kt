package com.github.faveroferreira.wishlist.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal
import java.util.*

data class ProductDTO(
    val id: UUID,

    val title: String,

    val brand: String,

    val price: BigDecimal,

    val image: String,

    @field:JsonProperty("review_score")
    val score: BigDecimal? = null,
)
