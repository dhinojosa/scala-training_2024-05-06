package com.xyzcorp

import com.xyzcorp.person.{American, NorthAmerican}
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class BoxSpec extends AnyFunSpec with Matchers {
    describe("A box") {
        it("should have a map") {
            val box = new Box("Hello")
            val result = box.map(s => s"$s!")
            result.value should be("Hello!")
        }
        it("should have a map that returns a different type") {
            val box: Box[String] = new Box("Hello")
            val result = box.map(s => s.length)
            result.value should be(5)
        }
        //        it("should have a invariant relationship") {
        //            val box:Box[American] = new Box[American](new American)
        //            val result:NorthAmerican = box.value
        //            result
        //        }
        it("should have a covariant relationship") {
            val box: Box[NorthAmerican] = new Box[American](new American)
            val result: NorthAmerican = box.value
            result
        }

        it("should have a covariant relationship and the ability to use update") {
            val box = new Box(100)
            val result = box.replace("Hello")
            println(result)
        }

        it("should have a covariant relationship and the ability to use update with a upper type") {
            val box:Box[NorthAmerican] = new Box[American](new American) //I did [+A]
            val result = box.replace(new American)
            println(result)
        }
    }
}
