package fridefors.shooter.room
import org.newdawn.slick.geom.{Rectangle, Shape}
import fridefors.general.{Agent, Vector, PositionalVector, Renderable}
import collection.mutable.ListBuffer
trait Interactive extends Agent {
  
  var position : Vector // [px]
  val box : Rectangle
  val boxOffset : Vector //how much the hit box is offset from center of position.
  
  private var sld = false //private version of solid
  def solid = sld
  def solid_= : Boolean => Unit = p => if (p != sld) {
    sld = p //setter
    if(p) Interactive.solids += this
    else Interactive.solids  -= this
  }
  
  def update() {
    val PositionalVector(x,y) = position + boxOffset
    box.setLocation(x,y)
  }
  
  def render(g: org.newdawn.slick.Graphics){
    g draw box
  }
  
  def move(dpos: Vector) {
    position += dpos
  }
  
  def allMeeting[A <: Interactive](dx: Float, dy: Float, lst: ListBuffer[A]) : ListBuffer[A] = {
    val newBox = new Rectangle(0, 0, box.getWidth(), box.getHeight())
    newBox.setLocation(position.x + dx + boxOffset.x, position.y + dy + boxOffset.y)
    lst filter {(other) => other != this && other.solid && (other.box intersects newBox)}
  }
  
  def objectMeeting[A <: Interactive](dx: Float, dy: Float, lst: ListBuffer[A]) : Option[A] = {
    val newBox = new Rectangle(0, 0, box.getWidth(), box.getHeight())
    newBox.setLocation(position.x + dx + boxOffset.x, position.y + dy + boxOffset.y)
    (lst.toStream filter {(other) => other != this && other.solid && (other.box intersects newBox)}).headOption
  }
  
  def anyMeeting[A <: Interactive](dx: Float, dy: Float, lst: ListBuffer[A]) = !(objectMeeting (dx,dy,lst) isEmpty)
  
}

object Interactive {
  val all    = ListBuffer[Interactive]()
  val solids = ListBuffer[Interactive]()
}