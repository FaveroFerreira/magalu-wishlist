package com.github.faveroferreira.wishlist.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "magalu")
data class MagaluConfigProps(
    val url: String,
)