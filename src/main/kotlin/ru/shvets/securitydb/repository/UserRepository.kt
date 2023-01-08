package ru.shvets.securitydb.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.shvets.securitydb.entity.User
import java.util.*

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  01.01.2023 13:28
 */

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): Optional<User>
    fun existsByEmail(email: String): Boolean
    fun deleteUserByEmail(email: String)

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "update users set logged_in_time=:time where id=:id")
    fun updateLogInTime(time: Long, id: Long)
}