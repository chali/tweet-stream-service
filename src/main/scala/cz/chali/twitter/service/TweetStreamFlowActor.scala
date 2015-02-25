package cz.chali.twitter.service

import akka.actor.Actor
import akka.stream.actor.{ActorPublisher, ActorSubscriber}
import akka.stream.FlowMaterializer
import akka.stream.scaladsl.{Sink, Source}
import org.reactivestreams.{Publisher, Subscriber}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.social.twitter.api.Tweet
import org.springframework.stereotype.Component

case class StartFlow(keywords: String, languageCodes: List[String])

@Component
@Scope("prototype")
class TweetStreamFlowActor extends Actor {

    @Autowired
    var springAwarePropsFactory: SpringAwarePropsFactory = _

    val materializer = FlowMaterializer()(context)

    override def receive: Receive = {
        case StartFlow(keywords, languageCodes) =>
            Source(twitterStreamPublisher(keywords))
                .filter(tweet => languageCodes.contains(tweet.getLanguageCode))
                .to(Sink(consoleStreamSubscriber()))
                .run()(materializer)
    }

    def twitterStreamPublisher(keywords: String): Publisher[Tweet] =
        ActorPublisher(
            context.actorOf(springAwarePropsFactory.props("twitterStreamHandlerActor", Seq(keywords)))
        )

    def consoleStreamSubscriber(): Subscriber[Tweet] =
        ActorSubscriber(context.actorOf(springAwarePropsFactory.props("consoleActorSubscriber")))
}
