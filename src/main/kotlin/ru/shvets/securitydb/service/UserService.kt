package ru.shvets.securitydb.service

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import ru.shvets.securitydb.dto.UserRegistrationDto
import ru.shvets.securitydb.entity.User
import java.util.*

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  01.01.2023 13:33
 */

interface UserService : UserDetailsService {
    val all: List<User>
    fun save(registrationDto: UserRegistrationDto): User
    fun delete(username: String)
    fun isExistUser(username: String): Boolean
    fun findByEmail(username: String): Optional<User>
    fun updateLoggedInTime(time: Long, id: Long)
}