package cz.chali.twitter.service

import javax.annotation.PostConstruct

import akka.actor.ActorRef
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ActorClient {

    @Autowired
    private var hiActorRef: ActorRef = _

    @Autowired
    private var twitterClientActorRef: ActorRef = _

    @PostConstruct
    def afterPropertiesSet(): Unit = {
        twitterClientActorRef ! TwitterClientRequest(hiActorRef)
    }
}
