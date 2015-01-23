package cz.chali.twitter.service

import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.social.twitter.api.{StreamingOperations, Twitter}
import org.springframework.social.twitter.api.impl.TwitterTemplate

@Configuration
class TwitterContext {

    @Bean
    def twitterTemplate(twitterSecurityProperties: TwitterSecurityProperties): Twitter =
        new TwitterTemplate(
            twitterSecurityProperties.getAppId, twitterSecurityProperties.getAppSecret,
            twitterSecurityProperties.getAccessToken, twitterSecurityProperties.getAccessTokenSecret)



    @Bean
    def streamingOperations(twitter: Twitter): StreamingOperations = twitter.streamingOperations()

}
