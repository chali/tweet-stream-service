package cz.chali.twitter.service.client

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("spring.social.twitter")
class TwitterSecurityProperties {

    private var appId: String = _
    private var appSecret: String = _
    private var accessToken: String = _
    private var accessTokenSecret: String = _

    def getAppId: String = appId
    def getAppSecret: String = appSecret
    def getAccessToken: String = accessToken
    def getAccessTokenSecret: String = accessTokenSecret

    def setAppId(appId: String): Unit = this.appId = appId
    def setAppSecret(appSecret: String): Unit = this.appSecret = appSecret
    def setAccessToken(accessToken: String): Unit = this.accessToken = accessToken
    def setAccessTokenSecret(accessTokenSecret: String): Unit = this.accessTokenSecret = accessTokenSecret
}
