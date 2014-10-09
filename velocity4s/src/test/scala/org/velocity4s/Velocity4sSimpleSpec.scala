package org.velocity4s

import org.scalatest.FunSpec
import org.scalatest.Matchers._

class Velocity4sSimpleSpec extends FunSpec with Velocity4sSpecSupport {
  describe("use StringTemplate") {
    it("simple") {
      val templateAsString = "Hello ${name}!!"
      val params = "name" -> "Velocity4s"

      eval(templateAsString, params) should be ("Hello Velocity4s!!")
    }
  }
}
