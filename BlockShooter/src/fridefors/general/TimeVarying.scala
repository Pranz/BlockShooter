package fridefors.general

class TimeVarying[A](initValue: A, f: A => A) {
  var currentValue = initValue
  def update() {
    currentValue = f(currentValue)
  }
  def get = currentValue
}