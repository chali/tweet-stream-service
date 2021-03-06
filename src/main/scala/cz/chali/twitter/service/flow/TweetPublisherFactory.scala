package cz.chali.twitter.service.flow

import akka.actor.ActorRefFactory
import akka.stream.actor.ActorPublisher
import cz.chali.twitter.service.spring.SpringAwarePropsFactory
import org.reactivestreams.Publisher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.social.twitter.api.Tweet
import org.springframework.stereotype.Component

@Component
class TweetPublisherFactory {

    @Autowired
    var springAwarePropsFactory: SpringAwarePropsFactory = _

    def create(keywords: String)(implicit context: ActorRefFactory): Publisher[Tweet] =
        ActorPublisher(
            context.actorOf(springAwarePropsFactory.props("tweetStreamActorPublisher", Seq(keywords)))
        )
}
