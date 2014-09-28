package org.velocity4s

import org.scalatest.FunSpec
import org.scalatest.Matchers._

class Velocity4sJavaClassSpec extends FunSpec with Velocity4sSpecSupport {
  describe("use StringTemplate") {
    it("Simple JavaBeans") {
      val templateAsString = "Hi, My name is $person.name!, $person.age years old"

      val (engine, templateName) = newEngineWithTemplate(templateAsString)
      val template = engine.getTemplate(templateName)
      val context = newContext("person" -> new JavaPerson("Ken", 15))

      merge(template, context) should be ("Hi, My name is Ken!, 15 years old")
    }
  }
}
