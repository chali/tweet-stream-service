package cz.chali.twitter.service.flow

import akka.actor.Actor
import akka.stream.scaladsl.{Sink, Source}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

case class StartFlow(keywords: String, languageCodes: List[String])

@Component
@Scope("prototype")
class TweetStreamFlowActor extends Actor {

    @Autowired
    var tweetPublisherFactory: TweetPublisherFactory = _
    @Autowired
    var tweetSubscriberFactory: TweetSubscriberFactory = _
    @Autowired
    var flowRunner: FlowRunner = _

    override def receive: Receive = {
        case StartFlow(keywords, languageCodes) =>
            val flow = Source(tweetPublisherFactory.create(keywords))
                .filter(tweet => languageCodes.contains(tweet.getLanguageCode))
                .to(Sink(tweetSubscriberFactory.create))
            flowRunner.run(flow, context)
    }
}
