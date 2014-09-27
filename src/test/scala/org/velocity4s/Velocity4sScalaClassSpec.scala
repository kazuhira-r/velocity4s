package org.velocity4s

import org.scalatest.FunSpec
import org.scalatest.Matchers._

class Velocity4sScalaClassSpec extends FunSpec with Velocity4sSpecSupport {
  describe("use StringTemplate") {
    it("Case Class") {
      val templateAsString = "Hi, My name is $person.name!, $person.age years old"
      val templateName = "template.vm"

      val engine = newSimplyEngine(templateName, templateAsString)
      val template = engine.getTemplate(templateName)
      val context = newContext("person" -> CasePerson("Ken", 15))

      merge(template, context) should be ("Hi, My name is Ken!, 15 years old")
    }

    it("Simple Class") {
      val templateAsString = "Hi, My name is $person.name!, $person.age years old"
      val templateName = "template.vm"

      val engine = newSimplyEngine(templateName, templateAsString)
      val template = engine.getTemplate(templateName)
      val context = newContext("person" -> new SimplePerson("Ken", 15))

      merge(template, context) should be ("Hi, My name is Ken!, 15 years old")
    }

    it("Method Invoke") {
      val templateAsString = "$greeting.say()"
      val templateName = "template.vm"

      val engine = newSimplyEngine(templateName, templateAsString)
      val template = engine.getTemplate(templateName)
      val context = newContext("greeting" -> new Greeting)

      merge(template, context) should be ("Hello Velocity4s")
    }
  }
}

case class CasePerson(name: String, age: Int)

class SimplePerson(val name: String, val age: Int)

class Greeting {
  def say: String =
    "Hello Velocity4s"
}
