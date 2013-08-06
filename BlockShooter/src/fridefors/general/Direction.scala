package fridefors.general

abstract class Direction { val vector: Vector }
case object Left extends Direction { val vector = Vector(-1, 0) }
case object Up extends Direction { val vector = Vector(0, -1) }
case object Down extends Direction { val vector = Vector(0, 1) }
case object Right extends Direction { val vector = Vector(1, 0) }
case object NoDir extends Direction { val vector = Vector(0, 0)}

object Direction {
  def horizontal(x: Float)  = if (x >= 0) Right else Left
  def vertical  (y: Float)  = if (y >= 0) Down  else Up
}