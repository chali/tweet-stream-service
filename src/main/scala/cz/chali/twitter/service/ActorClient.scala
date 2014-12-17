package cz.chali.twitter.service

import javax.annotation.PostConstruct

import akka.actor.ActorRef
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ActorClient {

    @Autowired
    private var actorRefFactory: ActorRefFactory = _

    @PostConstruct
    def afterPropertiesSet(): Unit = {
        val ref: ActorRef = actorRefFactory.getActorRef("hiActor")
        ref ! "saySomething"
    }
}
