package cz.chali.twitter.service

import akka.stream.actor.ActorPublisher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.social.twitter.api._
import org.springframework.social.twitter.api.impl.TwitterTemplate
import org.springframework.stereotype.Component
import scala.collection.JavaConverters._

private case class NewTweet(tweet: Tweet)

@Component
@Scope("prototype")
class TwitterStreamHandlerActor(keywords: String) extends ActorPublisher[Tweet] {

    @Autowired
    private var properties: TwitterSecurityProperties = _

    private var stream: Stream = _

    override def receive: Receive = {
        case NewTweet(tweet) =>
            if (totalDemand > 0) {
                onNext(tweet)
            }
    }

    @throws[Exception](classOf[Exception])
    override def preStart(): Unit = {
        val self = this.self
        val listeners: List[StreamListener] = List(new DefaultStreamListener {
            override def onTweet(tweet: Tweet): Unit = {
                self ! NewTweet(tweet)
            }
        })
        val twitter: Twitter = new TwitterTemplate(
            properties.getAppId, properties.getAppSecret,
            properties.getAccessToken, properties.getAccessTokenSecret)
        stream = twitter.streamingOperations().filter(keywords, listeners.asJava)
    }

    @throws[Exception](classOf[Exception])
    override def postStop(): Unit = stream.close()
}
