package cz.chali.twitter.service.flow

import akka.actor.ActorRefFactory
import akka.stream.ActorFlowMaterializer
import akka.stream.scaladsl.RunnableFlow
import org.springframework.stereotype.Component

@Component
class FlowRunner {

    def run[T](runnableFlow: RunnableFlow[T], actorRefFactory: ActorRefFactory): T = {
        val materializer = ActorFlowMaterializer()(actorRefFactory)
        runnableFlow.run()(materializer)
    }
}
