package org.velocity4s

import org.scalatest.FunSpec
import org.scalatest.Matchers._

class Velocity4sScalaClassSpec extends FunSpec with Velocity4sSpecSupport {
  describe("use StringTemplate") {
    it("Case Class") {
      val templateAsString = "Hi, My name is $person.name!, $person.age years old"
      val params = "person" -> CasePerson("Ken", 15)

      eval(templateAsString, params) should be ("Hi, My name is Ken!, 15 years old")
    }

    it("Simple Class") {
      val templateAsString = "Hi, My name is $person.name!, $person.age years old"
      val params = "person" -> new SimplePerson("Ken", 15)

      eval(templateAsString, params) should be ("Hi, My name is Ken!, 15 years old")
    }

    it("Simple Class2") {
      val templateAsString = "Hi, My name is $person.name!, $person.age years old"
      val params = "person" -> new SimplePerson2

      eval(templateAsString, params) should be ("Hi, My name is Taro!, 20 years old")
    }

    it("Method Invoke with parentheses") {
      val templateAsString = "$greeting.say()"
      val params = "greeting" -> new Greeting

      eval(templateAsString, params) should be ("Hello Velocity4s")
    }

    it("Method Invoke no parentheses") {
      val templateAsString = "$greeting.say"
      val params = "greeting" -> new Greeting

      eval(templateAsString, params) should be ("Hello Velocity4s")
    }

    it("Method Invoke with argument") {
      val templateAsString = "$greeting.say('Velocity')"
      val params = "greeting" -> new Greeting

      eval(templateAsString, params) should be ("Hello Velocity")
    }
  }
}

case class CasePerson(name: String, age: Int)

class SimplePerson(val name: String, val age: Int)

class SimplePerson2 {
  val name: String = "Taro"
  var age: Int = 20
}

class Greeting {
  def say: String =
    "Hello Velocity4s"

  def say(arg: String) =
    s"Hello $arg"
}
