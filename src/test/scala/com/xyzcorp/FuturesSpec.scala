package com.xyzcorp

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

import java.util.concurrent.Executors
import scala.concurrent.{ExecutionContext, Future}

class FuturesSpec extends AnyFunSpec with Matchers {
    describe("...") {
        it("...") {
            implicit val ec = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(2))
            val f = Future {
                400
            }
            f.map(i => i * 40).foreach(println)
        }
    }
}
