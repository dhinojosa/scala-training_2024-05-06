package com.xyzcorp

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class OptionSpec extends AnyFunSpec with Matchers {
   describe("how empty works") {
       it("...") {
           Option.empty[String]

       }
   }

   describe("partial function") {
       it("is a function that runs on a condition") {
           val p:PartialFunction[Int, String] = new PartialFunction[Int, String] {
               override def isDefinedAt(x: Int): Boolean = x % 3 == 0

               override def apply(v1: Int): String = s"3 * $v1"
           }

           val p2:PartialFunction[Int, String] = new PartialFunction[Int, String] {
               override def isDefinedAt(x: Int): Boolean = x % 2 == 0

               override def apply(v1: Int): String = s"2 * $v1"
           }

           val function = p.
           function
       }

   }


}

