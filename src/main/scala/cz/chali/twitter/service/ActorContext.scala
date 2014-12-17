package cz.chali.twitter.service

import akka.actor.ActorRef
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.{Bean, Configuration}

@Configuration
class ActorContext {
    @Autowired
    var actorRefFactory: ActorRefFactory = _

    @Bean
    def hiActorRef(): ActorRef = {
        actorRefFactory.getActorRef("hiActor")
    }
}
