package com.xyzcorp

import com.xyzcorp.person.{Person, Washingtonian}
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers


class FunctionSpec extends AnyFunSpec with Matchers {
   describe("A function") {
       it("uses Function1") {
           val myFunction = new Function[String, Int] {
               override def apply(v1: String): Int = v1.length
           }
           myFunction.apply("Hello") should be(5)
       }
       it("uses Function1 with a type") {
           val myFunction:String => Int = _.length
           myFunction.apply("Hello") should be(5)
       }
       it("uses Function 0") {
           val f0:() => Int = () => 1
       }
       it("has Function2") {
           val f2:(Int, String) => String = _ + _
       }
       it("has type inference for Function1") {
           //both
           val f1b:Int => Int = (x:Int) => x + 1

           //left
           val f1l:Int => Int = x => x + 1

           //right
           val f1r = (x:Int) => x + 1
       }
       it("has a postfix") {
           import scala.language.postfixOps
           val f:Int => Int = 1+
       }
       it("has a Box") {
           val b = new Box(120)
           val b2 = new Box("Hello")
           val b3:Box[Person] = new Box(new Washingtonian)
           b3.foo(new Person)
           val b4:Box[Washingtonian] = new Box(new Person)
           b4.foo(new Washingtonian)
       }
       it("a function returning a function as a closure") {
           def findAllWordsContainerAn(c:Char, s:List[String]) = ???

           def findAllWordsContainingAn(c:Char): List[String] => Int =
               (s:List[String]) => s.count(x => x.contains(c))

           val wordsContainingAnA = findAllWordsContainingAn('a')
           val wordsContainingAnZ = findAllWordsContainingAn('z')

           val strings = List("apple", "carrot", "zucchini", "pumpkin")
           println(wordsContainingAnA.apply(strings))
           println(wordsContainingAnZ.apply(strings))

       }
       it("has a map") {
           val result = List(1, 2, 3, 4, 5).map(i => i + "!")
           result should be (List("1!", "2!", "3!", "4!", "5!"))
       }
   }
}




