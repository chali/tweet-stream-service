package cz.chali.twitter.service

import akka.actor.ActorRefFactory
import akka.stream.actor.ActorSubscriber
import org.reactivestreams.Subscriber
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.social.twitter.api.Tweet
import org.springframework.stereotype.Component

@Component
class TweetSubscriberFactory {

    @Autowired
    var springAwarePropsFactory: SpringAwarePropsFactory = _

    def create(implicit context: ActorRefFactory): Subscriber[Tweet] =
        ActorSubscriber(context.actorOf(springAwarePropsFactory.props("consoleActorSubscriber")))
}
