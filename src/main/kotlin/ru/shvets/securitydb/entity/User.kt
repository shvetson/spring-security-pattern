package ru.shvets.securitydb.entity

import ru.shvets.securitydb.model.Role
import java.util.*
import javax.persistence.*

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  01.01.2023 13:17
 */

@Entity
@Table(name = "users", uniqueConstraints = [UniqueConstraint(columnNames = ["email"])])
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = 0L

    @Column(name = "first_name")
    var firstName: String? = null

    @Column(name = "last_name")
    var lastName: String? = null

    @Column(name = "email")
    var email: String? = null

    @Column(name = "password")
    var password: String? = null

    @Column(name = "is_disabled")
    var isDisabled: Boolean? = null

    @ElementCollection(targetClass = Role::class, fetch = FetchType.EAGER)
    @CollectionTable(name ="users_roles", joinColumns = [(JoinColumn(name = "user_id"))])
    @Enumerated(EnumType.STRING)
    var roles: Collection<Role>? = null

    @Column(name = "created_time", insertable = true, updatable = false)
    var created: Long? = null
    @Column(name = "modified_time")
    var modified: Long? = null
    @Column(name = "logged_in_time")
    var loggedIn: Long? = null

    constructor() {}
    constructor(
        firstName: String?,
        lastName: String?,
        email: String?,
        password: String?,
        isDisabled: Boolean?,
        roles: Collection<Role>?,
        created: Long?,
        modified: Long?,
        loggedIn: Long?
    ) : super() {
        this.firstName = firstName
        this.lastName = lastName
        this.email = email
        this.password = password
        this.isDisabled = isDisabled
        this.roles = roles
        this.created = created
        this.modified = modified
        this.loggedIn = loggedIn
    }

    @PrePersist
    fun onCreate() {
        this.created = Date().time
        this.modified = Date().time
    }

    @PreUpdate
    fun onUpdate() {
        this.modified = Date().time
    }
}