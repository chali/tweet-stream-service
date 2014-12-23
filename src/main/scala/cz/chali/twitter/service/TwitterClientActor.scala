package cz.chali.twitter.service

import akka.actor.{ActorRef, Actor}
import akka.pattern.pipe
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

import spray.http._
import spray.client.pipelining._

import scala.concurrent.Future

case class TwitterClientRequest(responseConsumer: ActorRef)

@Component
@Scope("prototype")
class TwitterClientActor extends Actor {

    import context.dispatcher

    override def receive: Receive = {
        case TwitterClientRequest(responseConsumer) =>
            val pipeline: HttpRequest => Future[HttpResponse] = sendReceive
            val response: Future[HttpResponse] = pipeline(Get("http://twitter.com/"))
            response.pipeTo(responseConsumer)
    }
}
