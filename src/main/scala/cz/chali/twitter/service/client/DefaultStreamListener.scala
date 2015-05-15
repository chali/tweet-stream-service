package cz.chali.twitter.service.client

import org.springframework.social.twitter.api.{StreamDeleteEvent, StreamListener, StreamWarningEvent, Tweet}

class DefaultStreamListener extends StreamListener {
    override def onTweet(tweet: Tweet): Unit = {}

    override def onLimit(numberOfLimitedTweets: Int): Unit = {}

    override def onWarning(warningEvent: StreamWarningEvent): Unit = {}

    override def onDelete(deleteEvent: StreamDeleteEvent): Unit = {}
}
