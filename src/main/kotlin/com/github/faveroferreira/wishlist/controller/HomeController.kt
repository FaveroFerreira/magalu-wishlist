package com.github.faveroferreira.wishlist.controller

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse

@RestController
class HomeController {

    @GetMapping
    fun redirectToSwagger(httpServletResponse: HttpServletResponse): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
            .header(HttpHeaders.LOCATION, "/swagger-ui.html")
            .body(null)
    }

}