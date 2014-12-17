package cz.chali.twitter.service

import akka.actor.{ActorRef, ActorSystem}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.{Bean, Configuration}

@Configuration
class ActorSystemContext {

    @Bean(destroyMethod = "shutdown")
    def actorSystem(): ActorSystem = {
        ActorSystem("springActorSystem")
    }
}
