package com.github.faveroferreira.wishlist.model

import java.math.BigDecimal
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "product")
class Product(

    @Id
    val id: UUID,

    val title: String,

    val brand: String,

    val price: BigDecimal,

    @Column(name = "image_url")
    val imageUrl: String,

    @Column(name = "review_score")
    val reviewScore: BigDecimal? = null,
)
