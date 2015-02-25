package cz.chali.twitter.service

import akka.actor.ActorSystem
import akka.stream.actor.{ActorSubscriber, ActorPublisher}
import org.reactivestreams.{Subscriber, Publisher}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.social.twitter.api.Tweet

@Configuration
class ActorContext {
    @Autowired
    var springAwarePropsFactory: SpringAwarePropsFactory = _

    @Autowired
    var actorSystem: ActorSystem = _

    @Bean
    def twitterStreamPublisher(): Publisher[Tweet] =
        ActorPublisher(
            actorSystem.actorOf(springAwarePropsFactory.props("twitterStreamHandlerActor", Seq("twitter")))
        )

    @Bean
    def consoleStreamSubscriber(): Subscriber[Tweet] =
        ActorSubscriber(actorSystem.actorOf(springAwarePropsFactory.props("consoleActorSubscriber")))
}
