package cz.chali.twitter.service

import akka.actor.ActorSystem
import org.springframework.context.annotation.{Bean, Configuration}

@Configuration
class ActorContext {

    @Bean(destroyMethod = "shutdown")
    def actorSystem(): ActorSystem = {
        ActorSystem("springActorSystem")
    }
}
