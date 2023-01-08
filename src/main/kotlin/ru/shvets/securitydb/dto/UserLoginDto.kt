package ru.shvets.securitydb.dto

import ru.shvets.securitydb.util.Constants
import javax.validation.constraints.*

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  25.12.2022 09:47
 */

class UserLoginDto {
    @field:NotBlank(message = "Invalid email: Empty email")
    @field:Email(message = "Invalid email format")
    @field:Pattern(
        regexp = Constants.VALID_EMAIL_ADDRESS_REGEX_WITH_EMPTY_SPACES_ACCEPTANCE,
        message = "Invalid email format"
    )
    lateinit var email: String

    @field:NotBlank(message = "Invalid password: Empty password")
    @field:Size(min = 6, max = 40, message = "Invalid password: Must be of 6 - 40 characters")
    lateinit var password: String

    constructor() {}
    constructor(email: String, password: String): super() {
        this.email = email
        this.password = password
    }
}