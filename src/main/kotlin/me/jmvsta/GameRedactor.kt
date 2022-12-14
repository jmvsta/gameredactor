package me.jmvsta

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties
class GameRedactor

fun main(args: Array<String>) {
    runApplication<GameRedactor>(*args)
}