package cz.chali.twitter.service

import org.springframework.social.twitter.api.{Tweet, StreamWarningEvent, StreamDeleteEvent, StreamListener}

class DefaultStreamListener extends StreamListener {
    override def onTweet(tweet: Tweet): Unit = {}

    override def onLimit(numberOfLimitedTweets: Int): Unit = {}

    override def onWarning(warningEvent: StreamWarningEvent): Unit = {}

    override def onDelete(deleteEvent: StreamDeleteEvent): Unit = {}
}
