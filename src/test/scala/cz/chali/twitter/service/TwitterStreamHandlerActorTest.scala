package cz.chali.twitter.service

import scala.concurrent.duration._
import akka.actor.ActorSystem
import akka.stream.ActorFlowMaterializer
import akka.stream.actor.ActorPublisher
import akka.stream.scaladsl.{Sink, Source}
import akka.testkit.{TestActorRef, TestKit}
import org.mockito.ArgumentCaptor
import org.mockito.Matchers._
import org.mockito.Matchers.{eq => mockEq}
import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}
import org.springframework.social.twitter.api._

import scala.concurrent.{Await, Future}

class TwitterStreamHandlerActorTest(_system: ActorSystem) extends TestKit(_system)
    with WordSpecLike with Matchers with MockitoSugar with BeforeAndAfterAll {

    def this() = this(ActorSystem("testSystem"))

    "An Actor" when {
        "created" should {
            "open stream, receive new tweets and close stream on stop" in {
                val keywords = "twitter"
                val expectedTweet = mock[Tweet]
                val stream = mock[Stream]
                val streamingOperations = mock[StreamingOperations]
                val listeners = ArgumentCaptor.forClass(classOf[java.util.List[StreamListener]])

                when(streamingOperations.filter(mockEq(keywords), listeners.capture())).thenReturn(stream)

                val actorRef = TestActorRef({
                    val actor: TwitterStreamHandlerActor = new TwitterStreamHandlerActor(keywords)
                    actor.streamingOperations = streamingOperations
                    actor
                })

                val resultFuture: Future[Tweet] = Source(ActorPublisher(actorRef))
                    .runWith(Sink.head[Tweet]())(ActorFlowMaterializer()(system))

                val listener = listeners.getValue.get(0)
                listener.onTweet(expectedTweet)

                val actualTweet = Await.result(resultFuture, 3 seconds)

                actorRef.stop()

                actualTweet shouldBe expectedTweet
                verify(streamingOperations).filter(mockEq(keywords), anyListOf(classOf[StreamListener]))
                verify(stream, times(1)).close()
            }
        }
    }
}
