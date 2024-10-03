package com.example.eventManagement.Security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.provisioning.JdbcUserDetailsManager
import org.springframework.security.provisioning.UserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import javax.sql.DataSource


@EnableWebSecurity
@Configuration
class SecurityConfig {
    @Bean
    fun authentication(): InMemoryUserDetailsManager{
        val admin1 = User.withUsername("dg")
            .password("dg123")
            .authorities("WRITE","READ")
            .build()
        val admin2 = User.withUsername("Ash")
            .password("ash123")
            .authorities("WRITE","READ")
            .build()
        val user1 = User.withUsername("sowmi")
            .password("sow123")
            .authorities("READ").build()
        return InMemoryUserDetailsManager(admin1,admin2,user1)
    }

    @Bean // Spring will manage the Datasource param
    fun detailsManager(dataSource: DataSource?): UserDetailsManager {
        val theUserDetailsManager = JdbcUserDetailsManager(dataSource)
        theUserDetailsManager.usersByUsernameQuery = "select name,password from users where name=?"
        theUserDetailsManager.setAuthoritiesByUsernameQuery("select name,role from users where name=?")
        return theUserDetailsManager
    }
    @Bean
    fun securityFilterChain(httpSecurity: HttpSecurity) : SecurityFilterChain{

        return httpSecurity.
        csrf{it.disable()}
            .authorizeHttpRequests{
                it.anyRequest().permitAll()
            }
            .httpBasic(Customizer.withDefaults())
            .build()
    }
}