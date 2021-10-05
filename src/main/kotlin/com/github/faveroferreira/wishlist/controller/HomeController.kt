package com.github.faveroferreira.wishlist.controller

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse

@RestController
class HomeController {

    @GetMapping
    fun redirectToSwagger(httpServletResponse: HttpServletResponse) {
        httpServletResponse.status = HttpStatus.MOVED_PERMANENTLY.ordinal
        httpServletResponse.setHeader(HttpHeaders.LOCATION, "http://localhost:8080/swagger-ui.html")
    }

}