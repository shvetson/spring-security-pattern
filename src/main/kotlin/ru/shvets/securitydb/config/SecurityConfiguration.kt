package ru.shvets.securitydb.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import ru.shvets.securitydb.service.UserService

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  01.01.2023 13:49
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfiguration(
    private var userService: UserService
) {

    @get:Bean
    val encoder: PasswordEncoder
        get() = PasswordEncoderFactories.createDelegatingPasswordEncoder()
//        return BCryptPasswordEncoder(12)

    @Bean
    fun authenticationProvider(): DaoAuthenticationProvider {
        val authenticationProvider = DaoAuthenticationProvider()
        authenticationProvider.setUserDetailsService(userService)
        authenticationProvider.setPasswordEncoder(encoder)
        return authenticationProvider
    }

    @Bean
    @Throws(Exception::class)
    fun authManager(http: HttpSecurity): AuthenticationManager? {
        val authenticationManagerBuilder = http.getSharedObject(
            AuthenticationManagerBuilder::class.java
        )
        authenticationManagerBuilder.authenticationProvider(authenticationProvider())
        return authenticationManagerBuilder.build()
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf().disable()
            .authorizeRequests()
            //Доступ для всех пользователей
                .antMatchers("/", "/auth/**", "/home", "/about", "/registration").permitAll()
            //Доступ только для пользователей с ролью Admin
                .antMatchers("/admin/**").hasAuthority("ADMIN")
            //Доступ только для пользователей с ролью User
                .antMatchers("/user/**").hasAnyAuthority("USER", "ADMIN")
            //Все остальные страницы требуют аутентификации
                .anyRequest().authenticated()

        http
            //Настройка для входа в систему
            .formLogin()
            .loginPage("/auth/login")
            //Перенарпавление на главную страницу после успешного входа
            .defaultSuccessUrl("/", true)
//                .failureUrl("/auth/login?error")
            .permitAll()
//            .usernameParameter("email")
//            .passwordParameter("password")
            .and()
            .logout()
            .logoutUrl("/logout")
//            .logoutRequestMatcher(AntPathRequestMatcher("/logout", "post"))
            .invalidateHttpSession(true)
            .clearAuthentication(true)
            .deleteCookies("JSESSIONID")
            .logoutSuccessUrl("/")
            .permitAll()

        return http.build()
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(SecurityConfiguration::class.java)
    }
}
