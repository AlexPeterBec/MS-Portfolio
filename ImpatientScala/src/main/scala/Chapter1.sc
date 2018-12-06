import scala.util.Random
import scala.math._

object Chapter1 {
  println("This is chapter 1")
  var sum = 8+8
  var sixteen = sum.toString()

  val greeting: String = "Hello"


  var biginttest: BigInt = 497601
  biginttest./%(62)

  // Pas de ++ en Scala
  biginttest+=1

  println("5. Plus de methodes")
  greeting.intersect("World")
  "Bonjour".sorted

  sqrt(3)
  pow(2, 4)
  min(3, Pi)

  BigInt.probablePrime(100, rnd=Random)

  println("6. La methode APPLY")
  val s: String = "Test String"
  s(5) // S


}