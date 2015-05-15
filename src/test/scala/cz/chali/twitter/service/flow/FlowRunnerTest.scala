package cz.chali.twitter.service.flow

import akka.actor.ActorSystem
import akka.stream.ActorFlowMaterializer
import akka.stream.scaladsl.RunnableFlow
import akka.testkit.TestKit
import org.mockito.Matchers._
import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{BeforeAndAfterAll, WordSpecLike}

class FlowRunnerTest (_system: ActorSystem) extends TestKit(_system) with WordSpecLike with MockitoSugar with BeforeAndAfterAll {

    def this() = this(ActorSystem("testSystem"))

    "A runner" should {
        "execute flow" in {
            val flow = mock[RunnableFlow[Unit]]

            new FlowRunner().run(flow, system)

            verify(flow).run()(any[ActorFlowMaterializer])
        }
    }
}
