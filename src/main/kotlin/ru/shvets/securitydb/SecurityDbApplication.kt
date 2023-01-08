package ru.shvets.securitydb

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SecurityDbApplication

fun main(args: Array<String>) {
    runApplication<SecurityDbApplication>(*args)
}
