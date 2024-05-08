package com.xyzcorp

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

import java.time.ZoneId

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


            List(1, 2, 3, 4, 5).sumOption should be(Some(15))
            List.empty[Int].sumOption should be(None)
            List(1.0, 2.0, 3.0, 4.0).sumOption should be(Some(10.0))
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

        it(
            """is done automatically in Scala because what is inside of scala.Predef, for example,
              |  it explains how be can set a scala.Float , and there is java.lang.Float,
              |  java primitive float.
              |  We can investigate this by looking at
              |  the documentation.""".stripMargin) {

            val f: scala.Float = 3000.0f
            val f2: scala.Float = 100.0f

            val result = java.lang.Math.min(f, f2)

            result.getClass.getName should be("float") //?

            val result2 = result + 100.0f

            result2.getClass.getName should be("float") //?

            result should be(100.0f)
        }
    }

    describe("Locating implicits recipes") {
        it(
            """has a common way, to store that particular implicit
              |  recipe in an object that makes should make
              |  sense and then import that object""".stripMargin) {

            //This should really be in it's own file
            object MyPredef {

                import scala.language.implicitConversions

                implicit class IntWrapper(x: Int) {
                    def isOdd: Boolean = x % 2 != 0

                    def isEven: Boolean = !isOdd
                }

            }

            import MyPredef._
            10.isEven should be(true)
        }

        it("""can also use a companion object to store any implicit recipes""".stripMargin) {
            class Artist(val firstName: String, val lastName: String)
            object Artist {

                import scala.language.implicitConversions

                implicit def tupleToArtist(t: (String, String)): Artist = new Artist(t._1, t._2)
            }

            def playArtist(a: Artist) = s"The music of ${a.firstName} ${a.lastName} is playing"

            playArtist("Stevie" -> "Wonder") should be("The music of Stevie Wonder is playing")
        }

        it("""can also use a package object to store some of these implicits""") {
            def numItems(list: List[String]): String = list.reduce(_ + _)

            numItems(List("One", "Two", "Three")) should be("OneTwoThree")
            numItems(4 -> "Wow") should be("WowWowWowWow")
        }

        it("""can use JavaConverters to convert a collection in Java to Scala and vice versa""") {
            import scala.jdk.CollectionConverters._
            val result = ZoneId.getAvailableZoneIds.asScala.toList
                .filter(_.startsWith("America")).map(s => s.split("/")(1)).filter(_.startsWith("E")).sorted
            println(result)
        }
    }

    describe("View Bounds are used to ensure that there is a particular recipe for a certain type") {
        it(
            """Uses <% inside of a parameterized type declaration to determine if there is a conversion available
              | then within you can treat an object as an object of that type. It is unorthodox, and has since been
              | deprecated.""".stripMargin) {

            trait Nameable {
                def firstName: String

                def lastName: String
            }

            class Employee(val firstName: String, val lastName: String) extends Nameable

            import scala.language.implicitConversions

            implicit def str2Employee(str: String): Employee = {
                str.split(" ").toList match {
                    case Nil => new Employee("John", "Doe")
                    case x :: Nil => new Employee(x, "Doe")
                    case fn :: ln :: _ => new Employee(fn, ln)
                }
            }

            //Use Case:
            // 1. You want to use a generic [A] [B] [V]
            // 2. You also want to make sure there implicit conversion recipe.
            //Bring A, but make sure there is an implicit conversion available
            def hireEmployee[A <% Employee](e: A) = {
                s"Hired ${e.firstName} ${e.lastName}"
            }

            //non-deprecated way
            def hireEmployee2[A](e: A)(implicit ev: A => Employee) = {
                s"Hired ${e.firstName} ${e.lastName}"
            }

            //non-deprecated way
            def hireEmployee3[A](e: A)(implicit ev: A => Nameable) = {
                s"Hired ${e.firstName} ${e.lastName}"
            }

            //             This one didn't work
            //            def hireEmployee4[A <: Nameable](e: A)(implicit ev: A => Nameable) = {
            //                s"Hired ${e.firstName} ${e.lastName}"
            //            }

            hireEmployee("Joe Armstrong") should be("Hired Joe Armstrong")
            hireEmployee2("Joe Armstrong") should be("Hired Joe Armstrong")
            hireEmployee3("Joe Armstrong") should be("Hired Joe Armstrong")
            //            hireEmployee4("Joe Armstrong") should be ("Hired Joe Armstrong")
        }

        describe(
            """Context Bounds works so that there is a type A, and it requires a B[A] somewhere
              |  within the the implicit scope, for example like Ordered[T], or TypeTag[T], or Numeric[T],
              |  this provides a way to check that something is something can be implicitly defined but
              |  gives the end user no opportunity to the ability to inject a different implementation""".stripMargin) {


            it(
                """uses the signature [T:WrappedType], which is
                  | equivalent to (t:T)(implicit w:WrappedType[T])
                  | let's try it with """.stripMargin) {

                trait Loggable[T] {
                    def log(t: T): String
                }

                class Employee(val firstName: String, val lastName: String)

                //Recipe for Employee
                implicit val loggableForEmployees = new Loggable[Employee] {
                    override def log(t: Employee): String = s"Employee(firstName = ${t.firstName}, lastName = ${t.lastName})"
                }

                def toStringz[T: Loggable](t: T): String = {
                    //We are now for implicit with Loggable[T]
                    val loggable = implicitly[Loggable[T]]
                    loggable.log(t)
                }

                toStringz(new Employee("Bjørn", "H")) should be("Employee(firstName = Bjørn, lastName = H)")
            }
        }
    }

}
