package cz.chali.twitter.service

import javax.annotation.PostConstruct

import org.reactivestreams.{Subscription, Subscriber, Publisher}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.social.twitter.api.Tweet
import org.springframework.stereotype.Component

@Component
class ActorClient {

    @Autowired
    private var twitterStreamPublisher: Publisher[Tweet] = _

    @PostConstruct
    def afterPropertiesSet(): Unit = {
        twitterStreamPublisher.subscribe(new Subscriber[Tweet]() {

            override def onSubscribe(s: Subscription): Unit =  {
                s.request(10)
            }

            override def onError(t: Throwable): Unit =  println(t)

            override def onComplete(): Unit =  println("complete")

            override def onNext(t: Tweet): Unit = {
                println(t.getText)
            }
        })
    }
}
