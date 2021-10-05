package com.github.faveroferreira.wishlist.service

import com.github.faveroferreira.wishlist.dto.ProductDTO
import com.github.faveroferreira.wishlist.exception.ProductNotFoundException
import com.github.faveroferreira.wishlist.http.MagaluHttpClient
import com.github.faveroferreira.wishlist.model.Product
import com.github.faveroferreira.wishlist.repository.ProductRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val magaluHttpClient: MagaluHttpClient,
) {

    fun findProductById(productId: UUID): Product {
        return findProductByIdLocally(productId) ?: findProductByIdExternally(productId)
    }

    private fun findProductByIdExternally(productId: UUID): Product {
        val productDTO: ProductDTO = magaluHttpClient.findProductById(productId) ?: throw ProductNotFoundException()

        return persistProduct(
            toProduct(productDTO)
        )
    }

    private fun persistProduct(product: Product): Product = productRepository.save(product)

    private fun findProductByIdLocally(productId: UUID): Product? = productRepository.findById(productId).orElse(null)

    private fun toProduct(productDTO: ProductDTO): Product = Product(
        id = productDTO.id,
        title = productDTO.title,
        brand = productDTO.brand,
        price = productDTO.price,
        imageUrl = productDTO.image,
        reviewScore = productDTO.score
    )

}
