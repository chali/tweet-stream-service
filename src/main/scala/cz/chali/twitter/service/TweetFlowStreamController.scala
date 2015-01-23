package cz.chali.twitter.service

import javax.annotation.PostConstruct

import org.reactivestreams.{Subscriber, Publisher}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.social.twitter.api.Tweet
import org.springframework.stereotype.Component

@Component
class TweetFlowStreamController {

    @Autowired
    private var twitterStreamPublisher: Publisher[Tweet] = _

    @Autowired
    private var consoleStreamSubscriber: Subscriber[Tweet] = _

    @PostConstruct
    def afterPropertiesSet(): Unit = {
        twitterStreamPublisher.subscribe(consoleStreamSubscriber)
    }
}
