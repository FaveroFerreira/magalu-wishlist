package com.github.faveroferreira.wishlist.util

import com.github.faveroferreira.wishlist.dto.ProductDTO
import com.github.faveroferreira.wishlist.model.Product
import java.math.BigDecimal
import java.math.RoundingMode.HALF_UP
import java.util.*

data class ProductDataBuilder(
    val id: UUID = UUID.randomUUID(),
    val title: String = "Sony Playstation 5 Slim",
    val brand: String = "Sony",
    val price: BigDecimal = BigDecimal("4864.59").setScale(2, HALF_UP),
    val image: String = "https://a-static.mlcdn.com.br/1500x1500/console-playstation-5-ps5-sony/magazineluiza/043079500/9d4f0cd7244929620b459cf9fd5e471c.jpg",
    val score: BigDecimal = BigDecimal("4.8").setScale(1, HALF_UP),
) {
    fun buildDTO() = ProductDTO(id, title, brand, price, image, score)

    fun buildModel() = Product(id, title, brand, price, image, score)
}
