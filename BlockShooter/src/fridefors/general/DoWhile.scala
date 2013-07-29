package fridefors.general

class DoWhile(p: => Boolean, f: => Unit, then: => Unit = {}) extends Updater {
  def init(): Unit = {
    DoWhile.all += this
  }
  
  def update() {
    if(p) f
    else destroy()
  }
  
  def destroy() {
    DoWhile.all -= this
    then
  }
}

object DoWhile {
  val all = collection.mutable.ListBuffer[DoWhile]()
}