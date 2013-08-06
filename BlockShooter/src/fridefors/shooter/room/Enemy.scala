package fridefors.shooter.room
import collection.mutable.ListBuffer
import org.newdawn.slick.geom.Rectangle
import fridefors.general.Vector

abstract class Enemy(val rm: Room, var position: Vector) extends Entity {
  
  val ai: Enemy => Unit
  
  override def update() {
    ai(this)
    super.update
  }
  
  override def init() {
    rm.enemies += this
    super.init()
  }
  
  override def destroy() {
    rm.enemies -= this
    super.destroy()
  }
  
}

object AI {
  val NoAI = (_:Enemy) => {}
}