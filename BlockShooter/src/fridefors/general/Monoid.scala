package fridefors.general

/**
 * A monoid is any type that has a binary operation returning
 * itself along with an identity constant that satisfies these laws:
 * > mReduce(a,identity) = a
 * > mReduce(identity,a) = a
 * > mReduce(a,b) = mReduce(b,a) //associativity
 * mConcat is the most natural way to reduce a whole list, mConcat [a,b] = mReduce a b
 */

trait Monoid[A] {
  val mReduce: (A, A) => A
  val identity: A
  lazy val mConcat: (A*) => A = _.foldRight(identity)(mReduce)
}

/**
 * Example of a monoid, the additive group with identity 0 and binary operation +
 */
object Additive extends Monoid[Double] {
  val identity = 0d
  val mReduce: (Double, Double) => Double = _ + _
  println(Additive.mConcat(1, 3, 4, 5))
}