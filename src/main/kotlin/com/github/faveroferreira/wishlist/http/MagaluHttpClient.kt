package com.github.faveroferreira.wishlist.http

import com.github.faveroferreira.wishlist.config.properties.MagaluConfigProps
import com.github.faveroferreira.wishlist.dto.ProductDTO
import com.github.faveroferreira.wishlist.exception.ProductNotFoundException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.util.*

@Component
class MagaluHttpClient(
    private val restTemplate: RestTemplate,
    private val magaluConfigProps: MagaluConfigProps,
) {

    private val log: Logger = LoggerFactory.getLogger(MagaluHttpClient::class.java)

    fun findProductById(productId: UUID): ProductDTO? {
        return try {
            val url = "${magaluConfigProps.url}/$productId/"
            restTemplate.getForEntity(url, ProductDTO::class.java).body!!
        } catch (e: Exception) {
            log.error("M=findProductById, message=Error finding product with id $productId", e)
            null
        }
    }

}