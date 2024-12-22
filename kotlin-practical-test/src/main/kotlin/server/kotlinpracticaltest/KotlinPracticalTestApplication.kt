package server.kotlinpracticaltest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class KotlinPracticalTestApplication

fun main(args: Array<String>) {
    runApplication<KotlinPracticalTestApplication>(*args)
}
