package fridefors.shooter.room
import fridefors.general.{Direction, Vector, Right, TimeVarying, Alarm}
abstract class Entity extends Physical {
  val maxSpd : Float   // [px/f]
  val spd    : Float   // [px/f]
  val runFactor: Float
  val airFactor: Float
  val jumpDelay: Int // [f]
  val jumpPower: Float // [m/f]
  val airJumpPower : Float // [m/f]
  val maxJumps : Int
  val friction : Float // [px/f^2]
  var effectiveFriction : Float = friction
  var currentJumps: Int = 0
  var walks = false
  var walkingDir : Direction = Right
  val spdFactor = new TimeVarying[Float](1, _ => (if (onGround) (if (running) runFactor else 1) else airFactor))
  val speed     = new TimeVarying[Float](spd, _ => spd * spdFactor.get)
  val maxSpeed  = new TimeVarying[Float](maxSpd, _ => maxSpd * (if (running) runFactor else 1))
  private var canMove = true
  
  def running : Boolean = false
  
  override def update() {
    spdFactor.update
    speed.update
    maxSpeed.update
    effectiveFriction = friction * (if (onGround) 1 else 0)
    if(onGround) currentJumps = maxJumps
    if(!walks){
      if(math.abs(velocity.x) <= effectiveFriction)velocity = velocity withX (0)
    }
    else if(walks && velocity.x != 0 && math.signum(walkingDir.vector.x) != math.signum(velocity.x)){
      velocity -= Vector(effectiveFriction * math.signum(velocity.x), 0)
    }
    if(!walks || math.abs(velocity.x) > maxSpd * (if (running) runFactor else 1)){
    	velocity -= Vector(effectiveFriction * math.signum(velocity.x), 0)
    }
    else walks = false
    super.update()
  }
  
  def walk(dir: Direction): Unit = if (canMove){
    walks = true
    walkingDir = dir
    if(math.signum(dir.vector.x) == math.signum(velocity.x) && math.abs(velocity.x + speed.get * dir.vector.x) >= math.abs(maxSpeed.get)){
      val spdDiff = velocity.x - maxSpeed.get*math.signum(velocity.x)
      if(-speed.get <= spdDiff && spdDiff <= speed.get) velocity = velocity withX (maxSpeed.get * math.signum(velocity.x))
    }
    else {
      velocity += dir.vector * speed.get
    }
  }
  
  def jump() {
    if(onGround) {
      pause(jumpDelay)
      new Alarm(jumpDelay, () => velocity = velocity.withY(-jumpPower))
    }
    else if(currentJumps != 0){
      velocity = velocity.withY(-airJumpPower)
      currentJumps -= 1
    }
  }
  
  def immobilize(time: Int) {
    canMove = false
    new Alarm(time, () => canMove = true)
  }
  
  def pause(time: Int) {
    canMove = false
    val xVel = velocity.x
    velocity = velocity.withX(0)
    new Alarm(time, () => {
      velocity = velocity withX xVel
      canMove = true
    })
  }
  
}