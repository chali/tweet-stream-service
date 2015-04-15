#Twitter Service [![Build Status](https://drone.io/github.com/chali/twitter-service/status.png)](https://drone.io/github.com/chali/twitter-service/latest)

Current purpose of this repository is playground for me. I try combine couple of frameworks and technology. How they play well together. Later I might add some other services.

Current feature is open stream of tweets based on keyword filter a and subscribe system output subscriber for this stream.

Technologies:
* Gradle
* Scala
* Spring Boot
* Akka
* Reactive Streams


TODO
* add tests
    * collisions in mockito and scala test matchers
* register stream for keyword api (router feature, test cluster support), api might be also queue
* some queue subscriber
* docker plugin create suspicious containers during build
* how to add external configuration
