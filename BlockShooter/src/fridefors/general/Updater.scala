package fridefors.general

import fridefors.shooter.room.Room

/**
 * An object that continuously updates in a room, and hence must belong to a room.
 */
trait Updater {
  def update(): Unit
  def destroy(): Unit
  def init(): Unit
  init()
}

trait Agent extends Updater with Renderable {
  val rm: Room
  def init() = rm.agents += this
  def destroy() = rm.agents -= this
}
