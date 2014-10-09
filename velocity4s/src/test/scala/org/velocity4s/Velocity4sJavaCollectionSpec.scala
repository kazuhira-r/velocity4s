package org.velocity4s

import org.scalatest.FunSpec
import org.scalatest.Matchers._

class Velocity4sJavaCollectionSpec extends FunSpec with Velocity4sSpecSupport {
  describe("use StringTemplate") {
    it("Java List foreach") {
      val templateAsString = """|#foreach ($name in $!names)
                                |$!name
                                |#end""".stripMargin
      val list = new java.util.ArrayList[String]
      list.add("Velocity")
      list.add("Scala")

      val params = "names" -> list

      eval(templateAsString, params) should be ("""|Velocity
                                                   |Scala
                                                   |""".stripMargin)
    }

    it("Java List get") {
      val templateAsString = "$!names[1]"

      val list = new java.util.ArrayList[String]
      list.add("Velocity")
      list.add("Scala")

      val params = "names" -> list

      eval(templateAsString, params) should be ("Scala")
    }

    it("Java Map foreach") {
      val templateAsString = """|#foreach ($e in $!map)
                                |$!e
                                |#end""".stripMargin

      val map = new java.util.HashMap[String, String]
      map.put("key", "Velocity4s")

      val params = "map" -> map

      eval(templateAsString, params) should be ("""|Velocity4s
                                                    |""".stripMargin)
    }

    it("Java Map access key") {
      val templateAsString = """$map.key"""

      val map = new java.util.HashMap[String, String]
      map.put("key", "Velocity4s")

      val params = "map" -> map

      eval(templateAsString, params) should be ("Velocity4s")
    }
  }
}
