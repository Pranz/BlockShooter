package fridefors.shooter

import org.newdawn.slick.{ Game, AppGameContainer, Graphics, GameContainer, Input }
import fridefors.general.{Alarm, Vector, DoWhile}
import fridefors.shooter.room.{Room, Player, GeneralEnemy, GameState}
import terrain.Block
/*
 * 
 * Style guide:
 * brackets '[]' written in comments denote units, such as px (pixels) or f (frames)
 */
class BlockShooter extends Game {
  def closeRequested(): Boolean = true
  def getTitle(): String = BlockShooter.TITLE

  def init(container: GameContainer): Unit = {
    BlockShooter.input = container.getInput()
    GameState.changeState(() => {
      val st = new TestRoom()
      st.init()
      st
    })
  }
  def update(container: GameContainer, delta: Int): Unit = {
    Alarm.all.clone foreach (_.update())
    DoWhile.all.clone foreach (_.update())
    GameState.update()
  }
  def render(container: GameContainer, g: Graphics): Unit = {
    
    GameState.render(g)
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

class TestRoom extends Room(){
  def init(){
    val player = new Player(this, Vector(100,200))
    players = List(player)
    new Block(this, Vector(50, 400), 16, 1)
    new Block(this, Vector(100, 320), 2, 2)
    new Block(this, Vector(200, 250), 1, 1)
    new Block(this, Vector(300, 340), 6, 1)
    new GeneralEnemy(this, Vector(400, 200))
  }
}