package ru.shvets.securitydb.controller

import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.ModelMap
import org.springframework.ui.set
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import ru.shvets.securitydb.dto.UserRegistrationDto
import ru.shvets.securitydb.entity.User
import ru.shvets.securitydb.service.UserService
import javax.validation.ConstraintValidatorContext
import javax.validation.Valid
import kotlin.reflect.full.createInstance

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  01.01.2023 13:57
 */

@Controller
@RequestMapping("/registration")
class RegistrationController(
    private val userService: UserService
) {
    @ModelAttribute("user")
    fun userRegistrationDto(): UserRegistrationDto {
        return UserRegistrationDto()
    }

    @GetMapping
    fun registration(): String {
        return "registration"
    }

    @PostMapping
    fun registration(@Validated @ModelAttribute("user") user: UserRegistrationDto,
        result: BindingResult,
        model: Model,
//        model: MutableMap<String, Any>
    ): String {

        if (result.hasErrors()) {
            val message = result.fieldError?.defaultMessage.toString()
            logger.error(message)
            model["wrong"] = message
            return "registration"
//            return "redirect:/registration?wrong"
        }

        if (user.email.let { userService.isExistUser(it) }) {
            model["unsuccessful"] = "User is exists already!"
            return "registration"
//            return "redirect:/registration?unsuccess"
        }

        userService.save(user)
        return "redirect:/registration?success"
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(RegistrationController::class.java)
    }
}