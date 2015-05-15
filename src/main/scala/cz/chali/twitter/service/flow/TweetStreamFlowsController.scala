package cz.chali.twitter.service.flow

import javax.annotation.PostConstruct

import akka.actor.{ActorRef, ActorSystem}
import akka.routing.FromConfig
import cz.chali.twitter.service.spring.SpringAwarePropsFactory
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
        router ! StartFlow("twitter", List("en"))
        router ! StartFlow("google", List("en"))
        router ! StartFlow("microsoft", List("en"))
        router ! StartFlow("amazon", List("en"))
    }

    private def createRouter(actorName: String): ActorRef = {
        actorSystem.actorOf(FromConfig.props(springAwarePropsFactory.props(actorName)), actorName)
    }
}
