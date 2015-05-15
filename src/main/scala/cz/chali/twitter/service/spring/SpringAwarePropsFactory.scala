package cz.chali.twitter.service.spring

import akka.actor._
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

@Component
class SpringAwarePropsFactory {

    @Autowired
    private var applicationContext: ApplicationContext = _

    def props(actorBeanName: String, args: Seq[Any] = Seq()): Props =
        Props(classOf[SpringActorProducer], applicationContext, actorBeanName, args)
}
