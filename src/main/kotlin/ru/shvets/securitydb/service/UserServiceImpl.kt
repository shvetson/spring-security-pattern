package ru.shvets.securitydb.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.shvets.securitydb.controller.AuthController
import ru.shvets.securitydb.dto.UserRegistrationDto
import ru.shvets.securitydb.model.Role
import ru.shvets.securitydb.entity.User
import ru.shvets.securitydb.repository.UserRepository
import java.util.*
import java.util.stream.Collectors

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  01.01.2023 13:37
 */

@Service
class UserServiceImpl(
    private val userRepository: UserRepository
) : UserService {

    override val all: List<User>
        get() = userRepository.findAll()

    override fun save(registrationDto: UserRegistrationDto): User {
        val user = User(
            registrationDto.firstName,
            registrationDto.lastName,
            registrationDto.email,
            encoder.encode(registrationDto.password),
            isDisabled = false,
            listOf(Role.USER),
//            Collections.singleton(Role.USER),
            created = 0L,
            modified = 0L,
            loggedIn = 0L
        )

        logger.info("User - ${registrationDto.email} successfully registered!")
        return userRepository.save(user)
    }

    override fun delete(username: String) {
        logger.info("User - $username successfully deleted!")
        userRepository.deleteUserByEmail(username)
    }

    override fun isExistUser(username: String): Boolean {
        return userRepository.existsByEmail(username)
    }

    override fun findByEmail(username: String): Optional<User> {
        return userRepository.findByEmail(username)
    }

    override fun updateLoggedInTime(time: Long, id: Long) {
        return userRepository.updateLogInTime(time, id)
    }

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user: User = findByEmail(username).orElseThrow { UsernameNotFoundException("There is not user with this email - $username!") }

        userRepository.updateLogInTime(Date().time, user.id!!)
        logger.info("User ${user.email} successfully logged in!")

        return org.springframework.security.core.userdetails.User.builder()
            .username(user.email)
            .password(user.password)
            .accountExpired(user.isDisabled!!)
            .accountLocked(user.isDisabled!!)
            .credentialsExpired(user.isDisabled!!)
            .disabled(user.isDisabled!!)
            .authorities(mapRolesToAuthorities(user.roles!!))
            .build()

//        return fromUserToUserDetails(user)
    }

    private fun mapRolesToAuthorities(roles: Collection<Role>): Collection<GrantedAuthority?> {
        return roles.stream().map { role ->
            SimpleGrantedAuthority(role.name)
        }.collect(Collectors.toList())
    }

    val encoder: PasswordEncoder
        get() = PasswordEncoderFactories.createDelegatingPasswordEncoder()

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(AuthController::class.java)
    }
}