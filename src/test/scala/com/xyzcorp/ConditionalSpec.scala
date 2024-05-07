package com.xyzcorp

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers


class ConditionalSpec extends AnyFunSpec with Matchers {
   describe("A conditional") {
       it("should return Less than 10 when given a small number") {
           val result:String = Conditional.tenStatus(3)
           result should be ("Less than 10")
       }
       it("should return Greater than 10 when given a large number") {
           val result:String = Conditional.tenStatus(14)
           result should be ("Greater than 10")
       }
       it("should return 'It is 10' when given a ten") {
           val result:String = Conditional.tenStatus(10)
           result should be ("It is 10!")
       }
   }
}
