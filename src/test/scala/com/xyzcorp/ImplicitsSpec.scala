package com.xyzcorp

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class ImplicitsSpec extends AnyFunSpec with Matchers {
    describe("implicits") {

        it("""can bring up any implicit directly by merely calling up implicitly""") {
            case class IceCream(name: String)
            case class Scoops(num: Int, flavor: IceCream)
            implicit val flavorOfTheMonth = IceCream("St. Patty's Cornbeef and Hash")

            def orderIceCream(num: Int)(implicit iceCream: IceCream) = Scoops(num, iceCream)

            def orderIceCream2(num: Int) = {
                Scoops(num, implicitly[IceCream])
            }

            orderIceCream2(2) should be(Scoops(2, flavorOfTheMonth))
        }

        it("""List[String] and List[Double]""") {
            implicit val strings = List("Fee", "Fie", "Foe")
            implicit val doubles = List(1.0, 2.0, 5.3)

            def zoom(x: Int)(implicit xs: List[String]) = {
                //The compiler will know this.
                xs.map(s => s + x)
            }

            zoom(4) should be(List("Fee4", "Fie4", "Foe4"))
        }

        it(
            """can be used for something like what Ruby has called
              |  monkey patching or Groovy calls mopping where we can add functionality to
              |  a class that we don't have access to, like isOdd/isEven
              |  in the Int class.  This is what we call implicit wrappers.
              |  First we will use a conversion method.""".stripMargin) {

            //[<Type>           , <Instance>    ]
            //[Int => IntWrapper, int2IntWrapper]

            import scala.language.implicitConversions

            class IntWrapper(x: Int) {
                def isOdd: Boolean = x % 2 != 0

                def isEven: Boolean = !isOdd
            }

            //method
            implicit def int2IntWrapper(x: Int): IntWrapper = new IntWrapper(x)

            40.isOdd should be(false)
            40.isEven should be(true)
        }


        it("""can have another example""") {
            implicit class ListWrapper[A: Numeric](x: List[A]) {
                def sumOption: Option[A] = if (x.isEmpty) None else Some(x.sum)
            }

            import scala.language.implicitConversions


            List(1,2,3,4,5).sumOption should be(Some(15))
            List.empty[Int].sumOption should be (None)
            List(1.0, 2.0, 3.0, 4.0).sumOption should be (Some(10.0))
        }

        it("can prevent me from calling certain methods if they don't match correctly") {
            val tuples: List[(String, String)] = List("Norway" -> "Oslo", "Mongolia" -> "Ulaan Battar")
            val map = tuples.toMap
            println(map)
        }

        it(
            """can also convert things to make it fit into a particular API,
              | this is called implicit conversion,
              | in this scenario we will use a method""".stripMargin) {
            import scala.language.implicitConversions

            sealed abstract class Currency
            case class Dollar(value: Int) extends Currency
            case class Yen(value: Int) extends Currency

            implicit def int2Dollar(x: Int): Dollar = Dollar(x)

            def addDollars(x: Dollar, y: Dollar): Dollar = Dollar(x.value + y.value)

            addDollars(40, 100) should be(Dollar(140))
        }

        it("Scala converts to BigInt automatically when needed") {
            val bigInt = BigInt(4000)
                (bigInt + 100) should be(BigInt(4100))
        }

    }
}
