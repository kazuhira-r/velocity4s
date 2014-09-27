package org.velocity4s

import org.scalatest.FunSpec
import org.scalatest.Matchers._

class Velocity4sSimpleSpec extends FunSpec with Velocity4sSpecSupport {
  describe("use StringTemplate") {
    it("simple") {
      val templateAsString = "Hello ${name}!!"
      val templateName = "template.vm"

      val engine = newSimplyEngine(templateName, templateAsString)
      val template = engine.getTemplate(templateName)
      val context = newContext("name" -> "Velocity4s")

      merge(template, context) should be ("Hello Velocity4s!!")
    }
  }
}
