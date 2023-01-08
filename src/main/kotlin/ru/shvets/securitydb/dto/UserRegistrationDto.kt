package ru.shvets.securitydb.dto

import ru.shvets.securitydb.entity.User
import ru.shvets.securitydb.model.Role
import ru.shvets.securitydb.util.Constants
import ru.shvets.securitydb.util.Constants.VALID_EMAIL_ADDRESS_REGEX_WITH_EMPTY_SPACES_ACCEPTANCE
import java.util.*
import javax.validation.constraints.*

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  01.01.2023 13:35
 */

class UserRegistrationDto {
    @field:NotBlank(message = "Invalid first name: Empty name")
    @field:Size(min = 3, max = 20, message = "Invalid first name: Must be of 3 - 20 characters")
    var firstName: String = ""

    @field:NotBlank(message = "Invalid last name: Empty name")
    @field:Size(min = 3, max = 50, message = "Invalid last name: Must be of 3 - 50 characters")
    var lastName: String = ""

    @field:NotBlank(message = "Invalid email: Empty email")
    @field:Email(message = "Invalid email format")
    @field:Pattern(
        regexp = VALID_EMAIL_ADDRESS_REGEX_WITH_EMPTY_SPACES_ACCEPTANCE,
        message = "Invalid email format"
    )
    var email: String = ""

    @field:NotBlank(message = "Invalid password: Empty password")
    @field:Size(min = 6, max = 40, message = "Invalid password: Must be of 6 - 40 characters")
    var password: String = ""

    constructor() {}
    constructor(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ) : super() {
        this.firstName = firstName
        this.lastName = lastName
        this.email = email
        this.password = password
    }
}