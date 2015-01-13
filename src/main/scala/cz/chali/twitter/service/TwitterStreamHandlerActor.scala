package cz.chali.twitter.service

import akka.actor.{ActorRef, Actor}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.social.twitter.api._
import org.springframework.social.twitter.api.impl.TwitterTemplate
import org.springframework.stereotype.Component
import scala.collection.JavaConverters._


case class OpenStream(responseConsumer: ActorRef)

@Component
@Scope("prototype")
class TwitterStreamHandlerActor extends Actor {

    @Autowired
    private var properties: TwitterSecurityProperties = _

    private var stream: Stream = _

    override def receive: Receive = {
        case OpenStream(responseConsumer) =>
            val listeners: List[StreamListener] = List(new StreamListener {
                override def onLimit(numberOfLimitedTweets: Int): Unit = {
                    print(numberOfLimitedTweets)
                }

                override def onWarning(warningEvent: StreamWarningEvent): Unit = {
                    print(warningEvent)
                }

                override def onDelete(deleteEvent: StreamDeleteEvent): Unit = {
                    print(deleteEvent)
                }

                override def onTweet(tweet: Tweet): Unit = {
                    responseConsumer ! tweet
                }
            })
            val twitter: Twitter = new TwitterTemplate(properties.getAppId, properties.getAppSecret,
                properties.getAccessToken, properties.getAccessTokenSecret)
            stream = twitter.streamingOperations().filter("twitter", listeners.asJava)
    }
}
