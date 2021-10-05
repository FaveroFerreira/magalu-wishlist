package com.github.faveroferreira.wishlist.controller

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthenticatedController {

    @PreAuthorize("hasRole('TOP_SECRET')")
    @GetMapping("/top-secret")
    fun securedResource() = "I am very super secret!"
}