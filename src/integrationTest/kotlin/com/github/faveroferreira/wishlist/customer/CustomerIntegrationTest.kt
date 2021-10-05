package com.github.faveroferreira.wishlist.customer

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.faveroferreira.wishlist.base.IntegrationTest
import com.github.faveroferreira.wishlist.model.Customer
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.*
import java.util.*

class CustomerIntegrationTest : IntegrationTest() {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun createNewCustomer() {
        val name = "New Name"
        val email = "brand.new@email.com"

        mockMvc.post("/customer") {
            contentType = APPLICATION_JSON
            content = """
                { "name": "$name", "email": "$email" }
            """.trimIndent()
        }.andDo {
            print()
        }.andExpect {
            status { isCreated() }
            jsonPath("\$.id") { exists() }
            jsonPath("\$.name") { value(name) }
            jsonPath("\$.email") { value(email) }
            jsonPath("\$.wishlist") { isArray() }
        }
    }

    @Test
    fun tryToCreateCustomerWithTakenEmail() {
        val name = "Taken Name"
        val email = "taken@email.com"

        createCustomer(name, email)

        mockMvc.post("/customer") {
            contentType = APPLICATION_JSON
            content = """
                { "name": "$name", "email": "$email" }
            """.trimIndent()
        }.andDo {
            print()
        }.andExpect {
            status { isBadRequest() }
            jsonPath("\$.message") { value("Email already taken!") }
        }
    }

    @Test
    fun updateCustomerName() {
        val outdatedName = "Old Name"
        val outdatedEmail = "old@mail.com"
        val newName = "Brand New Name"
        val newEmail = "new@mail.com"

        val capturedId = createCustomer(outdatedName, outdatedEmail)

        mockMvc.put("/customer/${capturedId!!}") {
            contentType = APPLICATION_JSON
            content = """
                { "name": "$newName", "email": "$newEmail" }
            """.trimIndent()
        }.andDo {
            print()
        }.andExpect {
            status { isOk() }
            jsonPath("\$.name") { value(newName) }
            jsonPath("\$.email") { value(newEmail) }
        }
    }

    @Test
    fun canListCustomers() {
        mockMvc.get("/customer").andExpect {
            status { isOk() }
        }
    }

    @Test
    fun canAddProductToCustomerWishlist() {
        val name = "I Have Products"
        val email = "product-wisher@mail.com"
        val createdCustomerId = createCustomer(name, email)

        val existingMagaluProductId = "cd54520e-3305-c20a-842a-92f1439576d4"

        mockMvc.post("/customer/$createdCustomerId/product-wishlist/$existingMagaluProductId") {
            contentType = APPLICATION_JSON
        }.andDo {
            print()
        }.andExpect {
            status { isOk() }
        }
    }

    private fun createCustomer(name: String, email: String): UUID? {
        var capturedId: UUID? = null

        mockMvc.post("/customer") {
            contentType = APPLICATION_JSON
            content = """
                    { "name": "$name", "email": "$email" }
                """.trimIndent()
        }.andDo {
            handle { capturedId = captureCustomerId(it) }
        }
        return capturedId
    }

    private fun captureCustomerId(it: MvcResult): UUID {
        return ObjectMapper().readValue<Customer>(it.response.contentAsString).id
    }
}