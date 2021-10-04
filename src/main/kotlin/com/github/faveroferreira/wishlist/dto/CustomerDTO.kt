package com.github.faveroferreira.wishlist.dto

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank


data class CustomerDTO(

    @field:NotBlank
    val name: String,

    @field:NotBlank
    @field:Email
    val email: String,
)