package fridefors.shooter.room
import collection.mutable.ListBuffer
import org.newdawn.slick.Graphics
import fridefors.general.Agent

class Room {
  val agents: ListBuffer[Agent] = ListBuffer()

  def update() {
    agents.clone() foreach {_.update}
  }

  def render(g:Graphics) {
    agents foreach {_ render g}
  }
}