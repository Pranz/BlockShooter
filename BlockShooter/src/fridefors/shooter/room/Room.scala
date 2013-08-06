package fridefors.shooter.room
import collection.mutable.ListBuffer
import org.newdawn.slick.{Graphics, Input}
import fridefors.general.{Agent, Updater}
import fridefors.shooter.BlockShooter

abstract class Room extends State {
  val agents: ListBuffer[Agent] = ListBuffer()
  val enemies: ListBuffer[Enemy] = ListBuffer()
  val interactives: ListBuffer[Interactive] = ListBuffer()
  val solids: ListBuffer[Interactive] = ListBuffer()
  def entities: List[Entity] = enemies.toList ++ players
  def player: Player = players(0)
  var players = List[Player]()
  var willReset = false
  
  def init():Unit

  def update() {
    interactives.clone() foreach {(iobj => iobj.previousPosition = iobj.position)}
    agents.clone() foreach {_.update}
  }

  def render(g:Graphics) {
    val debugList = List( 
        "x: " + player.position.x
      , "y: " + player.position.y
      , "dx: " + player.deltaPosition.x
      , "dy: " + player.deltaPosition.y
      , "vel.x: " + player.velocity.x
      , "vel.y: " + player.velocity.y
      , "on ground: " + player.onGround
      , "speed factor: " + player.spdFactor.get
      )
    for (i <- 0 to debugList.length - 1){
      g.drawString(debugList(i), 8, 8 + i*12)
    }
    agents foreach {_ render g}
  }
}

trait State {
  def update(): Unit
  def render(g:Graphics): Unit
  def init(): Unit
  
}

object PauseState extends State {
  var pausedState: State = null
  def init(){}
  def render(g:Graphics){
      pausedState.render(g)
  }
  def update(){}
}

object GameState extends State {
  
  var currentState: State = null
  //last function used to create a state
  var resetState: () => State = null
  var willReset: Boolean = false
  
  def update(){
    if(BlockShooter.input.isKeyPressed(Input.KEY_P)){
      pause(currentState)
    }
    
    if(willReset) {
      currentState = resetState()
      willReset = false
    }
    currentState.update()
    
  }
  
  def render(g: Graphics){
    currentState.render(g)
  }
  
  def init(){
    
  }
  
  def pause(st:State) {
    currentState match {
      case PauseState => transition(PauseState.pausedState)
      case (st:State) => {
        PauseState.pausedState = currentState
        transition(PauseState)
      }
    }
  }
  
  def reset() {
    willReset = true
  }
  
  def transition(st: State){
    currentState = st
  }
  
  def changeState(f: () => State){
    resetState = f
    currentState = f()
  }
  
}