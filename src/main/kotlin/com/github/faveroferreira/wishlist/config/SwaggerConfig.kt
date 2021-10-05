package com.github.faveroferreira.wishlist.config

import com.github.faveroferreira.wishlist.MagaluWishlistApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket

@Configuration

class SwaggerConfig {

    companion object {
        const val SERVICE_TITLE = "Magalu Challenge"
        const val DESCRIPTION = "Serviço responsável por manter a lista de desejos dos clientes"
        const val VERSION = "1.0.0"
        const val RESPONSIBLE_NAME = "Guilherme Favero Ferreira"
        const val RESPONSIBLE_WEBSITE = "https://www.linkedin.com/in/guilherme-favero-ferreira/"
        const val RESPONSIBLE_MAIL = "guifaveroferreira@gmail.com"
    }

    @Bean
    fun greetingApi(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage(MagaluWishlistApplication::class.java.packageName))
            .build()
            .pathMapping("/")
            .apiInfo(metaData())
            .useDefaultResponseMessages(false)
    }

    private fun metaData(): ApiInfo {
        return ApiInfoBuilder()
            .title(SERVICE_TITLE)
            .description(DESCRIPTION)
            .version(VERSION)
            .contact(Contact(RESPONSIBLE_NAME, RESPONSIBLE_WEBSITE, RESPONSIBLE_MAIL))
            .build()
    }

}