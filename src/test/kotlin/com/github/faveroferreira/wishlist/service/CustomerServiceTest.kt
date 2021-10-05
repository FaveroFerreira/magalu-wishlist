package com.github.faveroferreira.wishlist.service

import com.github.faveroferreira.wishlist.exception.CustomerEmailAlreadyTakenException
import com.github.faveroferreira.wishlist.exception.CustomerNotFoundException
import com.github.faveroferreira.wishlist.exception.DuplicateWishlistProductException
import com.github.faveroferreira.wishlist.model.Customer
import com.github.faveroferreira.wishlist.repository.CustomerRepository
import com.github.faveroferreira.wishlist.util.CustomerDataBuilder
import com.github.faveroferreira.wishlist.util.ProductDataBuilder
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

internal class CustomerServiceTest {


    @MockK
    lateinit var customerRepository: CustomerRepository

    @MockK
    lateinit var productService: ProductService

    @InjectMockKs
    lateinit var customerService: CustomerService

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
    }

    @Test
    fun `should be able to delete existing customer`() {
        val givenCustomerId = UUID.randomUUID()
        val givenCustomer = CustomerDataBuilder(id = givenCustomerId).buildModel()

        every { customerRepository.findById(givenCustomerId) } returns Optional.of(givenCustomer)
        every { customerRepository.delete(givenCustomer) } just Runs

        customerService.deleteCustomer(givenCustomerId)

        verify { customerRepository.delete(givenCustomer) }
    }

    @Test
    fun `should not be able to delete non existing customer`() {
        val givenCustomerId = UUID.randomUUID()

        every { customerRepository.findById(givenCustomerId) } returns Optional.empty()

        val result = runCatching { customerService.deleteCustomer(givenCustomerId) }

        assertThat(result.isFailure).isTrue
        assertThat(result.exceptionOrNull()).isInstanceOf(CustomerNotFoundException::class.java)
    }

    @Test
    fun `should be able to add a product to the wishlist`() {
        val givenProduct = ProductDataBuilder().buildModel()
        val givenCustomer = CustomerDataBuilder().buildModel()
        val givenProductId = givenProduct.id
        val givenCustomerId = givenCustomer.id
        val givenCustomerWithProduct = CustomerDataBuilder().buildModel().apply { wishlist += givenProduct }

        every { customerRepository.findById(givenCustomerId) } returns Optional.of(givenCustomer)
        every { productService.findProductById(givenProductId) } returns givenProduct
        every { customerRepository.save(any()) } returns givenCustomerWithProduct

        customerService.addProductToWishlist(givenCustomerId, givenProductId)

        verify { customerRepository.save(any()) }
    }

    @Test
    fun `should not add the same product to wishlist twice`() {
        val givenProduct = ProductDataBuilder().buildModel()
        val givenCustomer = CustomerDataBuilder().buildModel().apply { wishlist += givenProduct }
        val givenProductId = givenProduct.id
        val givenCustomerId = givenCustomer.id

        every { customerRepository.findById(givenCustomerId) } returns Optional.of(givenCustomer)
        every { productService.findProductById(givenProductId) } returns givenProduct

        val result = runCatching { customerService.addProductToWishlist(givenCustomerId, givenProductId) }

        assertThat(result.isFailure).isTrue
        assertThat(result.exceptionOrNull()).isInstanceOf(DuplicateWishlistProductException::class.java)
    }

    @Test
    fun `should not register two customers with the same e-mail`() {
        val givenNewCustomerDTO = CustomerDataBuilder().buildDTO()
        val givenCustomer = CustomerDataBuilder().buildModel()

        every { customerRepository.findByEmail(givenNewCustomerDTO.email) } returns givenCustomer

        val result = runCatching { customerService.createNewCustomer(givenNewCustomerDTO) }

        assertThat(result.isFailure).isTrue
        assertThat(result.exceptionOrNull())
            .isNotNull
            .isInstanceOf(CustomerEmailAlreadyTakenException::class.java)
    }

    @Test
    fun `should be able to create new customer when e-mail is not taken`() {
        val givenNewCustomerDTO = CustomerDataBuilder().buildDTO()
        val givenExpectedCreatedCustomer = CustomerDataBuilder().buildModel()

        every { customerRepository.findByEmail(givenNewCustomerDTO.email) } returns null
        every { customerRepository.save(any()) } returns givenExpectedCreatedCustomer

        val result = customerService.createNewCustomer(givenNewCustomerDTO)

        assertThat(result).isNotNull.isInstanceOf(Customer::class.java)
        assertThat(result.email).isEqualTo(givenNewCustomerDTO.email)
        assertThat(result.name).isEqualTo(givenNewCustomerDTO.name)
    }

    @Test
    fun `should not be able to update user e-mail when new e-mail is already taken`() {
        val givenUpdateCustomerDTO = CustomerDataBuilder(email = "another.email@gmail.com").buildDTO()
        val givenOutdatedCustomer = CustomerDataBuilder().buildModel()
        val givenCustomerId = givenOutdatedCustomer.id
        val givenEmailHolder =
            CustomerDataBuilder(id = UUID.randomUUID(), email = "another.email@gmail.com").buildModel()

        every { customerRepository.findById(givenCustomerId) } returns Optional.of(givenOutdatedCustomer)
        every { customerRepository.findByEmail(givenUpdateCustomerDTO.email) } returns givenEmailHolder

        val result = runCatching { customerService.updateCustomer(givenCustomerId, givenUpdateCustomerDTO) }

        assertThat(result.isFailure).isTrue
        assertThat(result.exceptionOrNull())
            .isNotNull
            .isInstanceOf(CustomerEmailAlreadyTakenException::class.java)
    }

    @Test
    fun `should be able to update user when new e-mail is not taken`() {
        val givenNewEmail = "my.new.email@gmail.com"
        val givenNewName = "Mister Junior Silva Santos"
        val givenCustomerId = UUID.randomUUID()

        val givenUpdateCustomerDTO = CustomerDataBuilder(email = givenNewEmail, name = givenNewName).buildDTO()
        val givenOutdatedCustomer = CustomerDataBuilder(id = givenCustomerId).buildModel()
        val givenUpdatedCustomer =
            CustomerDataBuilder(id = givenCustomerId, name = givenNewName, email = givenNewEmail).buildModel()

        every { customerRepository.findById(givenCustomerId) } returns Optional.of(givenOutdatedCustomer)
        every { customerRepository.findByEmail(givenNewEmail) } returns null
        every { customerRepository.save(givenUpdatedCustomer) } returns givenUpdatedCustomer

        val result = customerService.updateCustomer(givenCustomerId, givenUpdateCustomerDTO)

        assertThat(result).isNotNull.isEqualTo(givenUpdatedCustomer)
    }

}