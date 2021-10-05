package com.github.faveroferreira.wishlist.service

import com.github.faveroferreira.wishlist.exception.ProductNotFoundException
import com.github.faveroferreira.wishlist.http.MagaluHttpClient
import com.github.faveroferreira.wishlist.repository.ProductRepository
import com.github.faveroferreira.wishlist.util.ProductDataBuilder
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

internal class ProductServiceTest {

    @MockK
    lateinit var magaluHttpClient: MagaluHttpClient

    @MockK
    lateinit var productRepository: ProductRepository

    @InjectMockKs
    lateinit var productService: ProductService


    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
    }

    @Test
    fun `should throw error when product is not found anywhere`() {
        val givenProductId = UUID.randomUUID()

        every { productRepository.findById(givenProductId) } returns Optional.empty()
        every { magaluHttpClient.findProductById(givenProductId) } returns null

        val result = runCatching { productService.findProductById(givenProductId) }

        assertThat(result.isFailure).isTrue
        assertThat(result.exceptionOrNull()).isInstanceOf(ProductNotFoundException::class.java)

        verify { magaluHttpClient.findProductById(givenProductId) }
    }

    @Test
    fun `should get product from db when it is present`() {
        val givenProductId = UUID.randomUUID()
        val givenProduct = ProductDataBuilder(id = givenProductId).buildModel()

        every { productRepository.findById(givenProductId) } returns Optional.of(givenProduct)

        val result = productService.findProductById(givenProductId)

        assertThat(result).isNotNull.isEqualTo(givenProduct)

        verify(exactly = 0) { magaluHttpClient.findProductById(any()) }
    }

    @Test
    fun `should get product from external source when it is not present on db`() {
        val givenProductId = UUID.randomUUID()
        val givenExternalProductDTO = ProductDataBuilder(id = givenProductId).buildDTO()
        val givenProduct = ProductDataBuilder(id = givenProductId).buildModel()

        every { productRepository.findById(givenProductId) } returns Optional.empty()
        every { magaluHttpClient.findProductById(givenProductId) } returns givenExternalProductDTO
        every { productRepository.save(any()) } returns givenProduct

        val result = productService.findProductById(givenProductId)

        assertThat(result).isNotNull
        assertThat(result.id).isEqualTo(givenExternalProductDTO.id).isEqualTo(givenProductId)
        assertThat(result.imageUrl).isEqualTo(givenExternalProductDTO.image)

        verify { magaluHttpClient.findProductById(givenProductId) }
    }

}