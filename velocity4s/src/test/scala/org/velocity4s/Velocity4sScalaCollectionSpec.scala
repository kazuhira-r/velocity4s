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

      val params = "names" -> List("Velocity", "Scala")

      eval(templateAsString, params) should be ("""|Velocity
                                                   |Scala
                                                   |""".stripMargin)
    }

    it("Scala List apply") {
      val templateAsString = "$!names[1]"
      val params = "names" -> List("Velocity", "Scala")

      eval(templateAsString, params) should be ("Scala")
    }

    it("Scala List get => apply") {
      val templateAsString = "$!names.get(1)"
      val params = "names" -> List("Velocity", "Scala")

      eval(templateAsString, params) should be ("Scala")
    }

    it("Scala Map foreach") {
      val templateAsString = """|#foreach ($e in $!map)
                                |$!e
                                |#end""".stripMargin
      val params = "map" -> Map("key" -> "Velocity4s")

      eval(templateAsString, params) should be ("""|Velocity4s
                                                   |""".stripMargin)
    }

    it("Scala Map foreach keys") {
      val templateAsString = """|#foreach ($k in $!map.keys())
                                |$!k
                                |#end""".stripMargin
      val params = "map" -> Map("key" -> "Velocity4s")

      eval(templateAsString, params) should be ("""|key
                                                   |""".stripMargin)
    }

    it("Scala Map access key") {
      val templateAsString = """$map.key"""
      val params = "map" -> Map("key" -> "Velocity4s")

      eval(templateAsString, params) should be ("Velocity4s")
    }

    it("Scala Map key indexer") {
      val templateAsString = """$map["key"]"""
      val params = "map" -> Map("key" -> "Velocity4s")

      eval(templateAsString, params) should be ("Velocity4s")
    }

    it("Scala Map key indexer nested") {
      val templateAsString = """$map['nested']['key']"""
      val params = "map" -> Map("nested" -> Map("key" -> "Velocity4s"))

      eval(templateAsString, params) should be ("Velocity4s")
    }

    it("Scala Map missing key") {
      val templateAsString = """$map['missing']"""
      val params = "map" -> Map("key" -> "value")

      eval(templateAsString, params) should be ("$map['missing']")
    }

    it("Scala Map get") {
      val templateAsString = """$map.get('key')"""
      val params = "map" -> Map("key" -> "Velocity4s")

      eval(templateAsString, params) should be ("Velocity4s")
    }

    it("Scala Option Some foreach") {
      val templateAsString = """|#foreach ($v in $option)
                                |$v
                                |#end""".stripMargin
      val params = "option" -> Some("Velocity4s")

      eval(templateAsString, params) should be ("""|Velocity4s
                                                   |""".stripMargin)
    }

    it("Scala None Some foreach") {
      val templateAsString = """|#foreach ($v in $option)
                                |$v
                                |#end""".stripMargin
      val params = "option" -> None

      eval(templateAsString, params) should be (empty)
    }

    it("Scala Option Some get") {
      val templateAsString = "$option.get"
      val params = "option" -> Some("Velocity4s")

      eval(templateAsString, params) should be ("Velocity4s")
    }

    it("Scala Option Some get with parentheses") {
      val templateAsString = "$option.get()"
      val params = "option" -> Some("Velocity4s")

      eval(templateAsString, params) should be ("Velocity4s")
    }

    it("Scala Option None get") {
      val templateAsString = "$option.get"
      val params = "option" -> None

      eval(templateAsString, params) should be ("$option.get")
    }

    it("Scala Option None get with parentheses") {
      val templateAsString = "$option.get()"
      val params = "option" -> None

      eval(templateAsString, params) should be ("$option.get()")
    }

    it("Scala mutable.ArrayBuffer foreach") {
      val templateAsString = """|#foreach ($name in $!names)
                                |$!name
                                |#end""".stripMargin
      val params = "names" -> mutable.ArrayBuffer("Velocity", "Scala")

      eval(templateAsString, params) should be ("""|Velocity
                                                   |Scala
                                                   |""".stripMargin)
    }

    it("Scala mutable.ArrayBuffer apply") {
      val templateAsString = "$!names[1]"
      val params = "names" -> mutable.ArrayBuffer("Velocity", "Scala")

      eval(templateAsString, params) should be ("Scala")
    }

    it("Scala mutable.ArrayBuffer get => apply") {
      val templateAsString = "$!names.get(1)"
      val params = "names" -> mutable.ArrayBuffer("Velocity", "Scala")

      eval(templateAsString, params) should be ("Scala")
    }

    it("Scala mutable.Map foreach") {
      val templateAsString = """|#foreach ($e in $!map)
                                |$!e
                                |#end""".stripMargin
      val params = "map" -> mutable.Map("key" -> "Velocity4s")

      eval(templateAsString, params) should be ("""|Velocity4s
                                                   |""".stripMargin)
    }

    it("Scala mutable.Map access key") {
      val templateAsString = """$map.key"""
      val params = "map" -> mutable.Map("key" -> "Velocity4s")

      eval(templateAsString, params) should be ("Velocity4s")
    }
  }
}
