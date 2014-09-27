package org.velocity4s

import org.apache.velocity.util.introspection.Info

import org.scalatest.FunSpec
import org.scalatest.Matchers._

class ScalaUberspectImplSpec extends FunSpec {
  val dummyInfo: Info = new Info("Dummy.scala", 5, 5)

  describe("to JavaIterator Spec") {
    it("List to JavaIterator") {
      val uberspect = new ScalaUberspect
      uberspect.getIterator(List(1, 2, 3), dummyInfo) should be (a [java.util.Iterator[_]])
    }

    it("Map to JavaIterator") {
      val uberspect = new ScalaUberspect
      uberspect.getIterator(Map("key1" -> "value1",
                                "key2" -> "value2"), dummyInfo) should be (a [java.util.Iterator[_]])
    }

    it("Iterator to JavaIterator") {
      val uberspect = new ScalaUberspect
      uberspect.getIterator(List(1, 2, 3).iterator, dummyInfo) should be (a [java.util.Iterator[_]])
    }

    it("JavaList to JavaIterator") {
      val uberspect = new ScalaUberspect
      val list = new java.util.ArrayList[String]
      list.add("foo")
      list.add("bar")
      uberspect.getIterator(list, dummyInfo) should be (a [java.util.Iterator[_]])
    }
  }
}
