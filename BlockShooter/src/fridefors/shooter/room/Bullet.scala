package fridefors.shooter.room
import collection.mutable.ListBuffer

trait Bullet extends Physical {
  protected val prtc_immunes: List[Entity]
  val damages_players: Boolean
  val damages_enemies: Boolean
  var pierces: Option[Int]
  val interactsWithBlocks: Boolean
  def immunes = prtc_immunes ++ (if (damages_players) rm.players else List()) ++ (if (damages_enemies) rm.enemies else List())
  
  override def update(){
    allMeeting(0,0,rm.entities.asInstanceOf[ListBuffer[Entity]]).filter(obj => !(immunes contains obj)) foreach { ent =>
      if(pierces == Some(0)) destroy() else pierces map (_ - 1)
      interactWith(ent)
    }
    super.update()
  }
  
  def interactWith(ent: Entity)
  
}