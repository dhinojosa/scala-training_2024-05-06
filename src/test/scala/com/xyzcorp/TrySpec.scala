package com.xyzcorp

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

import scala.util.{Failure, Success, Try}

class TrySpec extends AnyFunSpec with Matchers {
    describe("Try") {

        def divideTwoNumbers(x: Int, y: Int): Try[Int] = Try {
            x / y
        }

        it(
            """is used to represent an object that may have failed.
              |  While option represents a valid possibility,
              |  Try represents an error""".stripMargin) {
            divideTwoNumbers(120, 2) should be(Success(60))
            divideTwoNumbers(101, 0) shouldBe a[Failure[?]]
        }

        it("""can also be pattern matched with from the Try""") {
            val result = divideTwoNumbers(120, 2) match {
                case Success(x) => s"Successful with value $x"
                case Failure(e) => s"Unsuccessful with message(${e.getMessage})"
            }
            result should be("Successful with value 60")
        }
        it("is also monadic! But first the non-for-comprehension way as a success") {
            val result = divideTwoNumbers(9, 3).flatMap(x => divideTwoNumbers(x, 1).map(y => x + y))
            result should be(Success(6))
        }
        it("is also monadic! But first the non-for-comprehension way as a failure") {
            val result = divideTwoNumbers(9, 3).flatMap(x => divideTwoNumbers(x, 0).map(y => x + y))
            result shouldBe a[Failure[_]]
            result match {
                case Success(i) => println(i)
                case Failure(e) => e.printStackTrace()
            }
        }
    }
}
