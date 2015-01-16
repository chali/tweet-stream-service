package cz.chali.twitter.service

import akka.actor.{Actor, IndirectActorProducer}
import org.springframework.context.ApplicationContext

class SpringActorProducer(ctx: ApplicationContext, actorBeanName: String, args: Seq[Any])
    extends IndirectActorProducer {

    override def produce(): Actor = ctx.getBean(actorBeanName, args.map(_.asInstanceOf[Object]): _*).asInstanceOf[Actor]

    override def actorClass: Class[_ <: Actor] = ctx.getType(actorBeanName).asInstanceOf[Class[_ <: Actor]]
}
