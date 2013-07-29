package fridefors.general

import collection.mutable.ListBuffer
/*
 * An alarm that executes its function after a number of frames.
 */
class Alarm(frames: Int, f: () => Unit) extends Updater {
  if (frames == 0) f()
  var current_tick = 1
  def update() = {
    if (frames == current_tick) {
      f()
      destroy()
    }
    current_tick += 1
  }
  def init() = Alarm.all += this
  def destroy() = Alarm.all -= this
}

object Alarm {
  val all = ListBuffer[Alarm]()
}