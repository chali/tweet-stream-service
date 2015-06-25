#Twitter Service [![Build Status](https://drone.io/github.com/chali/twitter-service/status.png)](https://drone.io/github.com/chali/twitter-service/latest)

Current purpose of this repository is playground for me. I try combine couple of frameworks and technology. How they play well together. Later I might add some other services.

Current feature is open stream of tweets based on keyword filter a and subscribe system output subscriber for this stream.

Technologies:
* Gradle
* Scala
* Spring Boot
* Akka
* Reactive Streams

##Twitter credentials
You can add external application.properties file following this [Description](http://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html#boot-features-external-config-application-property-files). Property file has to contain those properties.

    spring.social.twitter.app-id= #put your value
    spring.social.twitter.app-secret= #put your value
    spring.social.twitter.access-token= #put your value
    spring.social.twitter.access-token-secret= #put your value

TODO
* register stream for keyword api (router feature, verify cluster support), api might be also queue
* some queue subscriber