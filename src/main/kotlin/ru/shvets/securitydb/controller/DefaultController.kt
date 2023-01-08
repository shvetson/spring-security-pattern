package ru.shvets.securitydb.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  02.01.2023 11:04
 */

@Controller
class DefaultController {

    @GetMapping("/")
    fun index(): String {
        return "index"
    }

    @GetMapping("/home")
    fun home(): String {
        return "home"
    }

    @GetMapping("/admin")
    fun admin(): String {
        return "admin"
    }

    @GetMapping("/user")
    fun user(): String {
        return "user"
    }

    @GetMapping("/about")
    fun about(): String {
        return "about"
    }

    @GetMapping("/403")
    fun error403(): String {
        return "error/403"
    }
}