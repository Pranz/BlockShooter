package terrain

import fridefors.shooter.room.{Interactive, Room}
import fridefors.general.Vector
import org.newdawn.slick.geom.Rectangle

class Block(val rm: Room, var position : Vector, w: Int, h: Int) extends Interactive {
  val box = new Rectangle(0,0,w*32, h*32)
  val boxOffset = Vector(0,0)
  solid = true
}