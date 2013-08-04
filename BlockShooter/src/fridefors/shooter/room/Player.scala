package fridefors.shooter.room
import org.newdawn.slick.geom.Rectangle
import fridefors.general.{Vector, Left, Right, DoWhile}
import fridefors.shooter.BlockShooter
import org.newdawn.slick.Input

class Player(val rm: Room, var position: Vector) extends Entity {
  
  val box: Rectangle = new Rectangle(0,0,26,60)
  val boxOffset = Vector(0, 0)
  val gravity = 0.24f
  val airFactor = 0.1f
  val runFactor = 2f
  val jumpPower = 7f
  val jumpDelay = 0
  val maxJumps = 3
  val airJumpPower = 4f
  val spd = 0.3f
  val maxSpd = 2f
  val friction = 0.2f
  solid = false
  
  override def update() {
    if(BlockShooter.input.isKeyDown(Input.KEY_LEFT))  walk(Left)
    if(BlockShooter.input.isKeyDown(Input.KEY_RIGHT)) walk(Right)
    if(BlockShooter.input.isKeyPressed (Input.KEY_X)) jump()
    if(BlockShooter.input.isKeyPressed(Input.KEY_P)) {
      var i = 0
      new DoWhile({BlockShooter.input.isKeyDown(Input.KEY_P)}, {i += 1}, {println(i.toString)})
    }
    super.update()
  }
  
  override def running = BlockShooter.input.isKeyDown(Input.KEY_Z)

}