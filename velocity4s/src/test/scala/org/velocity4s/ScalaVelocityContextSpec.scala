package org.velocity4s

import java.io.{ StringReader, StringWriter }

import org.apache.velocity.app.VelocityEngine

import org.scalatest.FunSpec
import org.scalatest.Matchers._

class ScalaVelocityContextSpec extends FunSpec {
  describe("ScalaVelocityContext Instantiation Spec") {
    it("empty") {
      ScalaVelocityContext.empty should be (a[ScalaVelocityContext])
    }

    it("args java.util.Map") {
      val map = new java.util.HashMap[String, AnyRef]
      map.put("key", "value")

      ScalaVelocityContext(map) should be (a[ScalaVelocityContext])
    }

    it("args tulpe varargs") {
      ScalaVelocityContext("key1" -> "value1", "key2" -> "value2") should be (a[ScalaVelocityContext])
    }
  }

  describe("Scala friendly method Spec") {
    it("+=") {
      val context = ScalaVelocityContext.empty
      context += ("key1" -> "value1")
      context += ("key2" -> "value2")

      context.get("key1") should be ("value1")
      context.get("key2") should be ("value2")
    }

    it("+= varargs") {
      val context = ScalaVelocityContext.empty += ("key1" -> "value1", "key2" -> "value2")

      context.get("key1") should be ("value1")
      context.get("key2") should be ("value2")
    }

    it("++= Map") {
      val context = ScalaVelocityContext.empty ++= Map("key1" -> "value1", "key2" -> "value2")

      context.get("key1") should be ("value1")
      context.get("key2") should be ("value2")
    }

    it("++= List") {
      val context = ScalaVelocityContext.empty ++= List("key1" -> "value1", "key2" -> "value2")

      context.get("key1") should be ("value1")
      context.get("key2") should be ("value2")
    }

    it("-=") {
      val context = ScalaVelocityContext("key1" -> "value1", "key2" -> "value2")

      context -= "key2"

      context.get("key1") should be ("value1")
      context.get("key2") should be (null)
    }

    it("-= varargs") {
      val context = ScalaVelocityContext("key1" -> "value1", "key2" -> "value2", "key3" -> "value3")

      context -= ("key2", "key3")

      context.get("key1") should be ("value1")
      context.get("key2") should be (null)
      context.get("key3") should be (null)
    }

    it("--= List") {
      val context = ScalaVelocityContext("key1" -> "value1", "key2" -> "value2", "key3" -> "value3")

      context --= List("key2", "key3")

      context.get("key1") should be ("value1")
      context.get("key2") should be (null)
      context.get("key3") should be (null)
    }
  }

  describe("VelocityEngine compatibility Spec") {
    it("simple") {
      val velocity = new VelocityEngine
      velocity.init()

      val templateAsString = "${message} ${name}!!"
      val context = ScalaVelocityContext("message" -> "Hello", "name" -> "Velocity4s")

      val writer = new StringWriter
      velocity.evaluate(context, writer, getClass.getSimpleName, new StringReader(templateAsString))
      writer.toString should be ("Hello Velocity4s!!")
    }

    it("Java Compatibility") {
      val velocity = new VelocityEngine
      velocity.init()

      val templateAsString = """|#foreach ($e in $list)
                                |$e
                                |#end""".stripMargin

      val list = new java.util.ArrayList[Int]
      list.add(1)
      list.add(2)
      list.add(3)
      val context = ScalaVelocityContext("list" -> list)

      val writer = new StringWriter
      velocity.evaluate(context, writer, getClass.getSimpleName, new StringReader(templateAsString))
      writer.toString should be ("""|1
                                    |2
                                    |3
                                    |""".stripMargin)
    }

    it("Scala Compatibility") {
      val velocity = ScalaVelocityEngine.create
      velocity.init()

      val templateAsString = """|#foreach ($e in $list)
                                |$e
                                |#end""".stripMargin

      val context = ScalaVelocityContext("list" -> List(1, 2, 3))

      val writer = new StringWriter
      velocity.evaluate(context, writer, getClass.getSimpleName, new StringReader(templateAsString))
      writer.toString should be ("""|1
                                    |2
                                    |3
                                    |""".stripMargin)
    }
  }
}
