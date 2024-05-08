package com.xyzcorp

import com.xyzcorp.person.{Person, Washingtonian}
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

import java.time.Instant
import java.util.concurrent.TimeUnit
import scala.concurrent.duration.{Duration, FiniteDuration}


class FunctionSpec extends AnyFunSpec with Matchers {
    describe("A function") {
        it("uses Function1") {
            val myFunction = new Function[String, Int] {
                override def apply(v1: String): Int = v1.length
            }
            myFunction.apply("Hello") should be(5)
        }
        it("uses Function1 with a type") {
            val myFunction: String => Int = _.length
            myFunction.apply("Hello") should be(5)
        }
        it("uses Function 0") {
            val f0: () => Int = () => 1
        }
        it("has Function2") {
            val f2: (Int, String) => String = _ + _
        }
        it("has type inference for Function1") {
            //both
            val f1b: Int => Int = (x: Int) => x + 1

            //left
            val f1l: Int => Int = x => x + 1

            //right
            val f1r = (x: Int) => x + 1
        }
        it("has a postfix") {
            import scala.language.postfixOps
            val f: Int => Int = 1 +
        }
        it("has a Box") {
            val b = new Box(120)
            val b2 = new Box("Hello")
            val b3: Box[Person] = new Box(new Washingtonian)
            b3.foo(new Person)
            val b4: Box[Washingtonian] = new Box(new Person)
            b4.foo(new Washingtonian)
        }
        it("a function returning a function as a closure") {
            def findAllWordsContainerAn(c: Char, s: List[String]) = ???

            def findAllWordsContainingAn(c: Char): List[String] => Int =
                (s: List[String]) => s.count(x => x.contains(c))

            val wordsContainingAnA = findAllWordsContainingAn('a')
            val wordsContainingAnZ = findAllWordsContainingAn('z')

            val strings = List("apple", "carrot", "zucchini", "pumpkin")
            println(wordsContainingAnA.apply(strings))
            println(wordsContainingAnZ.apply(strings))

        }
        it("has a map") {
            val result = List(1, 2, 3, 4, 5).map(i => i + "!")
            result should be(List("1!", "2!", "3!", "4!", "5!"))
        }
        it("foldLeft") {
            def factorial(num: Int) = List.range(1, num + 1).foldLeft(1) { (total, next) =>
                println(s"total: $total\tnext: $next")
                total * next
            }

            val result = factorial(5)
            println(result)
        }
        it("foldRight") {
            def factorial(num: Int) = List.range(1, num + 1).foldRight(1) { (next, total) =>
                println(s"total: $total\tnext: $next")
                total * next
            }

            val result = factorial(5)
            println(result)
        }
        it("reduceLeft") {
            def factorial(num: Int) = List.range(1, num + 1).reduceLeft { (next, total) =>
                println(s"total: $total\tnext: $next")
                total * next
            }

            val result = factorial(5)
            println(result)
        }

        it("reduceLeftWithNoElements") {
            val result = List.empty[Int].reduceLeftOption { (next, total) => next + total }
            println(result)
        }

        it("calculate the average for a student throughout the semester") {
            def calculateAverage(xs: List[Int]): Option[Double] =
                xs.reduceLeftOption(_ + _).map(_.toDouble / xs.length)

            println(calculateAverage(List(80, 90, 100, 70, 50, 30, 80, 90, 100)))
            println(calculateAverage(List.empty[Int]))
            println(calculateAverage(List(0, 0)))
        }
    }

    describe("by-name parameter") {
        it("encapsulates an action as a block") {
            object Timer {
                def time[A](f: => A): (A, FiniteDuration) = {
                    val instant = Instant.now()
                    f -> Duration.apply(Instant.now().toEpochMilli - instant.toEpochMilli, TimeUnit.MILLISECONDS);
                }
            }

            val result = Timer.time {
                Thread.sleep(2000)
                400
            }

            result._1 should be(400)
            result._2.toMillis shouldBe > (2000L)
        }
    }
}




