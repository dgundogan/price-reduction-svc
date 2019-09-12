package co.uk.e2x

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class JohnLewisApplication {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<JohnLewisApplication>(*args)
        }
    }
}
