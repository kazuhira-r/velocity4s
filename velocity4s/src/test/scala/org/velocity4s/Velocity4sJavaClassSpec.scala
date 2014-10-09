package org.velocity4s

import org.scalatest.FunSpec
import org.scalatest.Matchers._

class Velocity4sJavaClassSpec extends FunSpec with Velocity4sSpecSupport {
  describe("use StringTemplate") {
    it("Simple JavaBeans") {
      val templateAsString = "Hi, My name is $person.name!, $person.age years old"
      val params = "person" -> new JavaPerson("Ken", 15)
      eval(templateAsString, params) should be ("Hi, My name is Ken!, 15 years old")
    }
  }
}
