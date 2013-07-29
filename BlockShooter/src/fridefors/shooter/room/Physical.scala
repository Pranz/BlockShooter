package fridefors.shooter.room
import fridefors.general.{Vector, Direction}
trait Physical extends Interactive {
  
  var velocity     : Vector = Vector(0,0) // [px/f]
  var acceleration : Vector = Vector(0,0)// [px/f^2]
  val gravity      : Float  // [px/f^2]
  
  override def update() {
    velocity += acceleration + Vector(0, gravity)
    move(velocity)
    super.update()
  }
  
  def onGround: Boolean = {
    anyMeeting(0, 1, Interactive.solids)
  }
  
  def moveUntilStop(dir: Direction){
    if(!anyMeeting(dir.vector.x, dir.vector.y, Interactive.solids)){
      position += dir.vector
      moveUntilStop(dir)
    }
  }
  
  def moveX(dx: Float) {
    if(!anyMeeting(dx,0,Interactive.solids)) position += Vector(dx,0)
    else {
      moveUntilStop(Direction horizontal dx)
      velocity = velocity withX 0
      acceleration = acceleration withX 0
    }
  }
  
  def moveY(dy: Float) {
    if(!anyMeeting(0,dy,Interactive.solids)) position += Vector(0,dy)
    else {
      moveUntilStop(Direction vertical dy)
      velocity = velocity withY 0
      acceleration = acceleration withY 0
    }
  }
  
  override def move(dpos: Vector) {
    moveX(dpos.x)
    moveY(dpos.y)
  }
  
}