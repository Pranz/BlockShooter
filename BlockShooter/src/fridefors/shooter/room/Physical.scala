package fridefors.shooter.room
import fridefors.general.{Vector, Direction}
trait Physical extends Interactive {
  
  var velocity     : Vector = Vector(0,0) // [px/f]
  var acceleration : Vector = Vector(0,0) // [px/f^2]
  val gravity      : Float                // [px/f^2]
  
  override def update() {
    velocity += acceleration + Vector(0, gravity)
    move(velocity)
    super.update()
  }
  
  def onGround: Boolean = {
    anyMeeting(0, 1, rm.solids)
  }
  
  def moveUntilStop(dir: Direction){
    if(!anyMeeting(dir.vector.x, dir.vector.y, rm.solids)){
      position += dir.vector
      moveUntilStop(dir)
    }
  }
  
}