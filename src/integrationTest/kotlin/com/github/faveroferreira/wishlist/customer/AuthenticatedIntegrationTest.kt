package com.github.faveroferreira.wishlist.customer

import com.github.faveroferreira.wishlist.base.IntegrationTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

class AuthenticatedIntegrationTest : IntegrationTest() {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun shouldNotAllowUnauthenticatedAccess() {
        mockMvc.get("/top-secret").andDo {
            print()
        }.andExpect {
            status { isUnauthorized() }
        }
    }

    @Test
    fun shouldNotAllowUnauthorizedUser() {
        mockMvc.get("/top-secret") {
            headers { setBasicAuth("not-magalu", "not-magalu") }
        }.andDo {
            print()
        }.andExpect {
            status { isUnauthorized() }
        }
    }


    @Test
    fun shouldAllowAuthorizedUser() {
        mockMvc.get("/top-secret") {
            headers { setBasicAuth("magalu", "magalu") }
        }.andDo {
            print()
        }.andExpect {
            status { isOk() }
        }
    }

}