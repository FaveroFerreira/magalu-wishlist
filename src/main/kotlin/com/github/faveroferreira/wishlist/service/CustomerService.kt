package com.github.faveroferreira.wishlist.service

import com.github.faveroferreira.wishlist.dto.CustomerDTO
import com.github.faveroferreira.wishlist.exception.CustomerEmailAlreadyTakenException
import com.github.faveroferreira.wishlist.exception.CustomerNotFoundException
import com.github.faveroferreira.wishlist.exception.DuplicateWishlistProductException
import com.github.faveroferreira.wishlist.model.Customer
import com.github.faveroferreira.wishlist.repository.CustomerRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class CustomerService(
    private val customerRepository: CustomerRepository,
    private val productService: ProductService,
) {

    fun addProductToWishlist(customerId: UUID, productId: UUID) {
        val customer = findCustomerById(customerId)
        val product = productService.findProductById(productId)

        if (customer.wishlist.contains(product)) throw DuplicateWishlistProductException()

        persistCustomer(customer.apply { wishlist += product })
    }

    fun deleteCustomer(customerId: UUID) = findCustomerById(customerId).let { customerRepository.delete(it) }

    fun updateCustomer(customerId: UUID, customerDTO: CustomerDTO): Customer {
        val updatingCustomer = findCustomerById(customerId)

        if (needsToUpdateEmail(updatingCustomer.email, customerDTO.email) &&
            newEmailBelongsToAnotherCustomer(updatingCustomer, customerDTO.email)
        ) {
            throw CustomerEmailAlreadyTakenException()
        }

        return persistCustomer(
            updatingCustomer.apply { name = customerDTO.name; email = customerDTO.email }
        )
    }

    fun createNewCustomer(customerDTO: CustomerDTO): Customer {
        if (emailIsAlreadyTaken(customerDTO.email)) {
            throw CustomerEmailAlreadyTakenException()
        }

        return persistCustomer(
            Customer(name = customerDTO.name, email = customerDTO.email)
        )
    }

    fun findAllCustomers(): List<Customer> = customerRepository.findAll()

    private fun newEmailBelongsToAnotherCustomer(updatingCustomer: Customer, email: String): Boolean {
        val currentEmailHolder = customerRepository.findByEmail(email) ?: return false

        return currentEmailHolder != updatingCustomer
    }

    private fun findCustomerById(customerId: UUID): Customer =
        customerRepository.findById(customerId).orElseThrow { CustomerNotFoundException() }

    private fun persistCustomer(customer: Customer): Customer =
        customerRepository.save(customer)

    private fun emailIsAlreadyTaken(email: String): Boolean =
        customerRepository.findByEmail(email) != null

    private fun needsToUpdateEmail(currentEmail: String, newEmail: String): Boolean =
        currentEmail != newEmail
}
