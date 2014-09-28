package org.velocity4s

import org.scalatest.FunSpec
import org.scalatest.Matchers._

class Velocity4sJavaCollectionSpec extends FunSpec with Velocity4sSpecSupport {
  describe("use StringTemplate") {
    it("Java List foreach") {
      val templateAsString = """|#foreach ($name in $!names)
                                |$!name
                                |#end""".stripMargin

      val (engine, templateName) = newEngineWithTemplate(templateAsString)
      val template = engine.getTemplate(templateName)

      val list = new java.util.ArrayList[String]
      list.add("Velocity")
      list.add("Scala")

      val context = newContext("names" -> list)

      merge(template, context) should be ("""|Velocity
                                             |Scala
                                             |""".stripMargin)
    }

    it("Java List get") {
      val templateAsString = "$!names[1]"

      val (engine, templateName) = newEngineWithTemplate(templateAsString)
      val template = engine.getTemplate(templateName)

      val list = new java.util.ArrayList[String]
      list.add("Velocity")
      list.add("Scala")

      val context = newContext("names" -> list)

      merge(template, context) should be ("Scala")
    }

    it("Java Map foreach") {
      val templateAsString = """|#foreach ($e in $!map)
                                |$!e
                                |#end""".stripMargin

      val (engine, templateName) = newEngineWithTemplate(templateAsString)
      val template = engine.getTemplate(templateName)

      val map = new java.util.HashMap[String, String]
      map.put("key", "Velocity4s")

      val context = newContext("map" -> map)

      merge(template, context) should be ("""|Velocity4s
                                             |""".stripMargin)

    }

    it("Java Map access key") {
      val templateAsString = """$map.key"""

      val (engine, templateName) = newEngineWithTemplate(templateAsString)
      val template = engine.getTemplate(templateName)

      val map = new java.util.HashMap[String, String]
      map.put("key", "Velocity4s")

      val context = newContext("map" -> map)

      merge(template, context) should be ("Velocity4s")

    }

  }
}
