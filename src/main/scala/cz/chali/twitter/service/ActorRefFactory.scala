package cz.chali.twitter.service

import akka.actor._
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

@Component
class ActorRefFactory {

    @Autowired
    private var applicationContext: ApplicationContext = _
    @Autowired
    private var actorSystem: ActorSystem = _

    def getActorRef(actorBeanName: String): ActorRef = actorSystem.actorOf(props(actorBeanName))

    private def props(actorBeanName: String): Props = Props(classOf[SpringActorProducer], applicationContext, actorBeanName)
}
