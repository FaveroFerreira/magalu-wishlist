package com.github.faveroferreira.wishlist

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication
class MagaluWishlistApplication

fun main(args: Array<String>) {
	runApplication<MagaluWishlistApplication>(*args)
}
