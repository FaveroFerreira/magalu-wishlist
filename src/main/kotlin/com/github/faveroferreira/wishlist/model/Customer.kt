package com.github.faveroferreira.wishlist.model

import java.util.*
import javax.persistence.*
import javax.persistence.CascadeType.*
import javax.persistence.FetchType.LAZY
import kotlin.collections.HashSet

@Entity
@Table
class Customer(

    @Id
    val id: UUID = UUID.randomUUID(),

    var name: String,

    var email: String,

    @ManyToMany(fetch = LAZY, cascade = [PERSIST, MERGE, REMOVE])
    @JoinTable(
        name = "customer_wishlist_product",
        joinColumns = [JoinColumn(name = "customer_id")],
        inverseJoinColumns = [JoinColumn(name = "product_id")]
    )
    var wishlist: Set<Product> = HashSet(),
)