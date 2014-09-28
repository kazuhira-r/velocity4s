package org.velocity4s

import scala.collection._

import org.scalatest.FunSpec
import org.scalatest.Matchers._

class Velocity4sScalaCollectionSpec extends FunSpec with Velocity4sSpecSupport {
  describe("use StringTemplate") {
    it("Scala List foreach") {
      val templateAsString = """|#foreach ($name in $!names)
                                |$!name
                                |#end""".stripMargin

      val (engine, templateName) = newEngineWithTemplate(templateAsString)
      val template = engine.getTemplate(templateName)
      val context = newContext("names" -> List("Velocity", "Scala"))

      merge(template, context) should be ("""|Velocity
                                             |Scala
                                             |""".stripMargin)
    }

    it("Scala List apply") {
      val templateAsString = "$!names[1]"

      val (engine, templateName) = newEngineWithTemplate(templateAsString)
      val template = engine.getTemplate(templateName)
      val context = newContext("names" -> List("Velocity", "Scala"))

      merge(template, context) should be ("Scala")
    }

    it("Scala Map foreach") {
      val templateAsString = """|#foreach ($e in $!map)
                                |$!e
                                |#end""".stripMargin

      val (engine, templateName) = newEngineWithTemplate(templateAsString)
      val template = engine.getTemplate(templateName)

      val context = newContext("map" -> Map("key" -> "Velocity4s"))

      merge(template, context) should be ("""|(key,Velocity4s)
                                             |""".stripMargin)

    }

    it("Scala Map access key") {
      val templateAsString = """$map.key"""

      val (engine, templateName) = newEngineWithTemplate(templateAsString)
      val template = engine.getTemplate(templateName)
      val context = newContext("map" -> Map("key" -> "Velocity4s"))

      merge(template, context) should be ("Velocity4s")

    }

    it("Scala Map key indexer") {
      val templateAsString = """$map["key"]"""

      val (engine, templateName) = newEngineWithTemplate(templateAsString)
      val template = engine.getTemplate(templateName)
      val context = newContext("map" -> Map("key" -> "Velocity4s"))

      merge(template, context) should be ("Velocity4s")
    }

    it("Scala Map key indexer nested") {
      val templateAsString = """$map['nested']['key']"""

      val (engine, templateName) = newEngineWithTemplate(templateAsString)
      val template = engine.getTemplate(templateName)
      val context = newContext("map" -> Map("nested" -> Map("key" -> "Velocity4s")))

      merge(template, context) should be ("Velocity4s")
    }

    it("Scala Map missing key") {
      val templateAsString = """$map['missing']"""

      val (engine, templateName) = newEngineWithTemplate(templateAsString)
      val template = engine.getTemplate(templateName)
      val context = newContext("map" -> Map("key" -> "value"))

      merge(template, context) should be ("$map['missing']")
    }

    it("Scala Map get") {
      val templateAsString = """$map.get('key')"""

      val (engine, templateName) = newEngineWithTemplate(templateAsString)
      val template = engine.getTemplate(templateName)
      val context = newContext("map" -> Map("key" -> "Velocity4s"))

      merge(template, context) should be ("Velocity4s")
    }

    it("Scala Option Some foreach") {
      val templateAsString = """|#foreach ($v in $option)
                                |$v
                                |#end""".stripMargin

      val (engine, templateName) = newEngineWithTemplate(templateAsString)
      val template = engine.getTemplate(templateName)
      val context = newContext("option" -> Some("Velocity4s"))

      merge(template, context) should be ("""|Velocity4s
                                             |""".stripMargin)
    }

    it("Scala None Some foreach") {
      val templateAsString = """|#foreach ($v in $option)
                                |$v
                                |#end""".stripMargin

      val (engine, templateName) = newEngineWithTemplate(templateAsString)
      val template = engine.getTemplate(templateName)
      val context = newContext("option" -> None)

      merge(template, context) should be (empty)
    }
  }

    it("Scala mutable.ArrayBuffer foreach") {
      val templateAsString = """|#foreach ($name in $!names)
                                |$!name
                                |#end""".stripMargin

      val (engine, templateName) = newEngineWithTemplate(templateAsString)
      val template = engine.getTemplate(templateName)
      val context = newContext("names" -> mutable.ArrayBuffer("Velocity", "Scala"))

      merge(template, context) should be ("""|Velocity
                                             |Scala
                                             |""".stripMargin)
    }

    it("Scala mutable.ArrayBuffer apply") {
      val templateAsString = "$!names[1]"

      val (engine, templateName) = newEngineWithTemplate(templateAsString)
      val template = engine.getTemplate(templateName)
      val context = newContext("names" -> mutable.ArrayBuffer("Velocity", "Scala"))

      merge(template, context) should be ("Scala")
    }

    it("Scala mutable.Map foreach") {
      val templateAsString = """|#foreach ($e in $!map)
                                |$!e
                                |#end""".stripMargin

      val (engine, templateName) = newEngineWithTemplate(templateAsString)
      val template = engine.getTemplate(templateName)

      val context = newContext("map" -> mutable.Map("key" -> "Velocity4s"))

      merge(template, context) should be ("""|(key,Velocity4s)
                                             |""".stripMargin)

    }

    it("Scala mutable.Map access key") {
      val templateAsString = """$map.key"""

      val (engine, templateName) = newEngineWithTemplate(templateAsString)
      val template = engine.getTemplate(templateName)
      val context = newContext("map" -> mutable.Map("key" -> "Velocity4s"))

      merge(template, context) should be ("Velocity4s")

    }

}
