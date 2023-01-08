package ru.shvets.securitydb.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import ru.shvets.securitydb.dto.UserLoginDto
import ru.shvets.securitydb.service.UserService

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  01.01.2023 13:56
 */

@Controller
@RequestMapping("/auth")
class AuthController(
    private val userService: UserService
) {

    @ModelAttribute("user")
    fun userLoginDto(): UserLoginDto {
        return UserLoginDto()
    }

    @GetMapping("/login")
    fun login(): String {
        return "login"
    }

    @PostMapping("/login")
    fun login(@ModelAttribute("user") userLoginDto: UserLoginDto): String {
        return "login"
    }

    @GetMapping("/success")
    fun getSuccess(model: Model): String {
        return "index"
    }

//    @PostMapping("/logout")
//    fun logout(model: Model): String {
//        return "redirect:/auth/login?logout"
//    }

//    @GetMapping("/logout")
//    fun logout(request: HttpServletRequest): String? {
//        val authentication: Authentication? = SecurityContextHolder.getContext().authentication
//        if (authentication != null) {
//            request.session.invalidate()
//        }
//        return "index"
//        return "redirect:/"
//    }
}