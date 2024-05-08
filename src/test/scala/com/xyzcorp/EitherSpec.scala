package com.xyzcorp

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class EitherSpec extends AnyFunSpec with Matchers {
    def doThis(): Either[String, Int] =
        Right(10)

    def doThat(): Either[String, Int] =
        Left("This failed")

    def doTheOther(): Either[String, Int] =
        Right(90)


    describe("An either") {
        it("this is a different to handle things that could go wrong") {
            val result = doThis().flatMap(x => doTheOther().map(y => x + y))
            result should be(Right(100))
        }
        it("has cond") {
            val x = 100
            val result = Either.cond(x > 0, x, "Number must be positive")
            result
        }
        it("is monadic, and therefore you can for comprehension") {
            val result = for {x <- doThis()
                              z <- doTheOther()} yield x + z
            println(result)
        }
    }


}
