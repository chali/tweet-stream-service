package cz.chali.twitter.service

import javax.annotation.PostConstruct

import akka.actor.{ActorSystem, ActorRef}
import akka.routing.FromConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class TweetStreamFlowsController {

    @Autowired
    var springAwarePropsFactory: SpringAwarePropsFactory = _
    @Autowired
    private var actorSystem: ActorSystem = _

    @PostConstruct
    def afterPropertiesSet(): Unit = {
        val router: ActorRef = createRouter("tweetStreamFlowActor")
        router ! StartFlow("twitter")
        router ! StartFlow("google")
        router ! StartFlow("microsoft")
        router ! StartFlow("amazon")
    }

    private def createRouter(actorName: String): ActorRef = {
        actorSystem.actorOf(FromConfig.props(springAwarePropsFactory.props(actorName)), actorName)
    }
}
