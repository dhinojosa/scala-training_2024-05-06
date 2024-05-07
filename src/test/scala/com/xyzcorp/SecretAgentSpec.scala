package com.xyzcorp

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class SecretAgentSpec extends AnyFunSpec with Matchers {
    describe("a specification") {
        it("...") {
            val bond = new SecretAgent("James Bond")
            val felix = new SecretAgent("Felix Leitner")
            val jason = new SecretAgent("Jason Bourne")
            val _99 = new SecretAgent("99")
            val max = new SecretAgent("Max Smart")

            bond.shoot(800)
            felix.shoot(200)
            jason.shoot(150)
            _99.shoot(150)
            max.shoot(200)
            println(SecretAgent.bullets)
        }
    }
}
