package server.kotlinstart

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinStartApplication

fun main(args: Array<String>) {
    runApplication<KotlinStartApplication>(*args)
}
