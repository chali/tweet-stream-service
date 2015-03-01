package cz.chali.twitter.service

import akka.actor.ActorSystem
import akka.stream.actor.ActorPublisher
import akka.testkit.{TestActorRef, TestKit}
import org.mockito.ArgumentCaptor
import org.mockito.Matchers._
import org.mockito.Matchers.{eq => mockEq}
import org.mockito.Mockito._
import org.reactivestreams.{Subscription, Subscriber}
import org.scalatest.mock.MockitoSugar
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}
import org.springframework.social.twitter.api._

class TwitterStreamHandlerActorTest(_system: ActorSystem) extends TestKit(_system)
    with WordSpecLike with Matchers with MockitoSugar with BeforeAndAfterAll {

    def this() = this(ActorSystem("testSystem"))

    "An Actor" when {
        "created" should {
            "open stream and receive new tweets" in {
                val keywords = "twitter"
                val stream = mock[Stream]
                val streamingOperations = mock[StreamingOperations]
                val listeners = ArgumentCaptor.forClass(classOf[java.util.List[StreamListener]])

                when(streamingOperations.filter(mockEq(keywords), listeners.capture())).thenReturn(stream)

                val actorRef = TestActorRef({
                    val actor: TwitterStreamHandlerActor = new TwitterStreamHandlerActor(keywords)
                    actor.streamingOperations = streamingOperations
                    actor
                })

                val tweet = mock[Tweet]
                var tweetSubscribed = false

                ActorPublisher(actorRef).subscribe(new Subscriber[Tweet]() {
                    override def onSubscribe(s: Subscription): Unit = s.request(1)

                    override def onError(t: Throwable): Unit = {}

                    override def onComplete(): Unit = {}

                    override def onNext(t: Tweet): Unit = {
                        t shouldBe tweet
                        tweetSubscribed = true
                    }
                })

                val listener = listeners.getValue.get(0)
                listener.onTweet(tweet)

                actorRef.stop()

                tweetSubscribed shouldBe true
                verify(streamingOperations).filter(mockEq(keywords), anyListOf(classOf[StreamListener]))
                verify(stream, times(1)).close()
            }
        }
    }
}
