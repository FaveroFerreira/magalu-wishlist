package com.github.faveroferreira.wishlist.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig : WebSecurityConfigurerAdapter() {

    private val customer = arrayOf(
        "/customer",
        "/customer/**"
    )

    private val swagger = arrayOf(
        "/",
        "/csrf",
        "/v2/api-docs",
        "/swagger-resources",
        "/swagger-resources/**",
        "/configuration/ui",
        "/configuration/security",
        "/swagger-ui.html",
        "/webjars/**"
    )

    override fun configure(http: HttpSecurity) {
        http
            .authorizeRequests()
            .antMatchers(*swagger).permitAll()
            .antMatchers(*customer).permitAll()
            .antMatchers("/**").authenticated()
            .and()
            .httpBasic()

        http.csrf().disable()
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.inMemoryAuthentication()
            .withUser("magalu")
            .password(passwordEncoder().encode("magalu"))
            .roles("TOP_SECRET")
            .and()
            .withUser("not-magalu")
            .password("not-magalu")
            .roles("NOT_THAT_SECRET")
    }


    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

}