package cz.chali.twitter.service

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class Application

object Application extends App {
    val configurationClasses: Array[AnyRef] = Array(classOf[Application])
    SpringApplication.run(configurationClasses, args)
}
