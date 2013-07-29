package fridefors.shooter

import org.newdawn.slick.{ Game, AppGameContainer, Graphics, GameContainer, Input }
import fridefors.general.{Alarm, Vector, DoWhile}
import fridefors.shooter.room.{Room, Player}
import terrain.Block
/*
 * 
 * Style guide:
 * brackets '[]' written in comments denote units, such as px (pixels) or f (frames)
 */
class BlockShooter extends Game {
  def closeRequested(): Boolean = true
  def getTitle(): String = BlockShooter.TITLE
  var rm: Room = null
  var player: Player = null

  def init(container: GameContainer): Unit = {
    BlockShooter.input = container.getInput()
    rm = new Room()
    new Block(rm, Vector(50, 400), 16, 2)
    player = new Player(rm, Vector(100,200))
  }
  def update(container: GameContainer, delta: Int): Unit = {
    Alarm.all.clone foreach (_.update())
    DoWhile.all.clone foreach (_.update())
    rm.update()
  }
  def render(container: GameContainer, g: Graphics): Unit = {
    val debugList = List( 
      "x: " + player.position.x
    , "y: " + player.position.y
    , "vel.x: " + player.velocity.x
    , "vel.y: " + player.velocity.y
    , "on ground: " + player.onGround
    , "speed factor: " + player.spdFactor.get
    , "objects: " + rm.agents.toString
    , "alarms: "  + Alarm.all.toString
    , "dowhile: " + DoWhile.all.toString
    )
    for (i <- 0 to debugList.length - 1){
      g.drawString(debugList(i), 8, 8 + i*12)
    }
    rm.render(g)
  }
}

object BlockShooter {

  var input: Input = null
  val TITLE = "BANG BANG!!111"
  val HEIGHT = 480
  val WIDTH = 640

  def createGameContainer(): AppGameContainer = {
    val app = new AppGameContainer(new BlockShooter(), WIDTH, HEIGHT, false);
    app.setUpdateOnlyWhenVisible(false);
    app.setTitle(TITLE);
    app.setShowFPS(false);
    app.setTargetFrameRate(60);
    app;
  }
}

object Main {
  def main(args: Array[String]) {
    val container = BlockShooter.createGameContainer()
    container start

  }
}