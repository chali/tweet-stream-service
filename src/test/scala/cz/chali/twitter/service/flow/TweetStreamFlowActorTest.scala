package cz.chali.twitter.service.flow

import akka.actor.{ActorRefFactory, ActorSystem}
import akka.stream.scaladsl.RunnableFlow
import akka.testkit.{TestActorRef, TestKit}
import org.reactivestreams.{Subscriber, Publisher}
import org.scalatest.mock.MockitoSugar
import org.mockito.Matchers.{eq => mockEq, _}
import org.mockito.Mockito._
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}
import org.springframework.social.twitter.api.Tweet

class TweetStreamFlowActorTest (_system: ActorSystem) extends TestKit(_system)
with WordSpecLike with Matchers with MockitoSugar with BeforeAndAfterAll {

    def this() = this(ActorSystem("testSystem"))

    "An Actor" when {
        "receives start flow request" should {
            "create flow and start it" in {
                val keywords = "twitter"
                val languages = List("en")
                val tweetPublisher = mock[Publisher[Tweet]]
                val tweetSubscriber = mock[Subscriber[Tweet]]
                val tweetPublisherFactory = mock[TweetPublisherFactory]
                val tweetSubscriberFactory = mock[TweetSubscriberFactory]
                val flowRunner = mock[FlowRunner]

                when(tweetPublisherFactory.create(mockEq(keywords))(any(classOf[ActorRefFactory])))
                    .thenReturn(tweetPublisher)
                when(tweetSubscriberFactory.create(any(classOf[ActorRefFactory])))
                    .thenReturn(tweetSubscriber)

                val actorRef = TestActorRef({
                    val actor: TweetStreamFlowActor = new TweetStreamFlowActor()
                    actor.tweetPublisherFactory = tweetPublisherFactory
                    actor.tweetSubscriberFactory = tweetSubscriberFactory
                    actor.flowRunner = flowRunner
                    actor
                })

                actorRef ! StartFlow(keywords, languages)

                verify(flowRunner).run(any(classOf[RunnableFlow[Tweet]]), any(classOf[ActorRefFactory]))
            }
        }
    }
}