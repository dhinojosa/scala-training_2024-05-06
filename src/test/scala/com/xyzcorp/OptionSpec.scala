package com.xyzcorp

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class OptionSpec extends AnyFunSpec with Matchers {
   describe("how empty works") {
       it("...") {
           Option.empty[String]

       }
   }
}

