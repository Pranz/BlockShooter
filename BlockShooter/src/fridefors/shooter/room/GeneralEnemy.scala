package fridefors.shooter.room
import fridefors.general.{Vector, Direction, NoDir}
import org.newdawn.slick.geom.Rectangle

class GeneralEnemy(rmr: Room, pos: Vector) extends Enemy(rmr, pos) {
  val box: Rectangle = new Rectangle(0,0,26,60)
  val boxOffset = Vector(0, 0)
  val gravity = 0.24f
  val airFactor = 0.1f
  val runFactor = 2f
  val jumpPower = 7f
  val jumpDelay = 0
  val maxJumps = 0
  val airJumpPower = 4f
  val spd = 0.15f
  val maxSpd = 1.5f
  val friction = 0.1f
  solid = false
  var diff = 0f
  var previousRunning = false
  
  override def update() {
    diff = rm.player.position.x - position.x
    previousRunning = running
    super.update
  }
  
  val ai = (ent:Enemy) => {
    val dir = if (Math.abs(diff) <= 6) NoDir else Direction.horizontal(diff)
    if(!ent.anyMeeting(dir.vector.x, 0, rm.solids)) ent.walk(dir)
    else jump()
  }
  
  override def running = if (!previousRunning) Math.abs(diff) >= 100 else Math.abs(diff) >= 40

}