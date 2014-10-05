package org.velocity4s

import scala.collection._

import org.apache.velocity.util.introspection.{ Info, VelMethod }
import org.apache.velocity.util.introspection.UberspectImpl

import org.scalatest.FunSpec
import org.scalatest.Matchers._

class ScalaUberspectSpec extends FunSpec {
  val dummyInfo: Info = new Info("Dummy.scala", 5, 5)

  describe("to JavaIterator Spec") {
    it("List to JavaIterator") {
      val uberspect = new ScalaUberspect
      uberspect.init()
      uberspect.getIterator(List(1, 2, 3), dummyInfo) should be (a[java.util.Iterator[_]])
    }

    it("Map to JavaIterator") {
      val uberspect = new ScalaUberspect
      uberspect.init()
      uberspect.getIterator(Map("key1" -> "value1",
        "key2" -> "value2"), dummyInfo) should be (a[java.util.Iterator[_]])
    }

    it("mutable.ArrayBuffer to JavaIterator") {
      val uberspect = new ScalaUberspect
      uberspect.init()
      uberspect.getIterator(mutable.ArrayBuffer(1, 2, 3), dummyInfo) should be (a[java.util.Iterator[_]])
    }

    it("mutable.Map to JavaIterator") {
      val uberspect = new ScalaUberspect
      uberspect.init()
      uberspect.getIterator(mutable.Map("key1" -> "value1",
        "key2" -> "value2"), dummyInfo) should be (a[java.util.Iterator[_]])
    }

    it("Iterator to JavaIterator") {
      val uberspect = new ScalaUberspect
      uberspect.init()
      uberspect.getIterator(List(1, 2, 3).iterator, dummyInfo) should be (a[java.util.Iterator[_]])
    }

    it("JavaList to JavaIterator") {
      val uberspect = new ScalaUberspect
      uberspect.init()
      val list = new java.util.ArrayList[String]
      list.add("foo")
      list.add("bar")
      uberspect.getIterator(list, dummyInfo) should be (a[java.util.Iterator[_]])
    }

    it("Option(Some) to JavaIterator") {
      val uberspect = new ScalaUberspect
      uberspect.init()
      uberspect.getIterator(Some("Velocity4s"), dummyInfo) should be (a[java.util.Iterator[_]])
    }

    it("None to JavaIterator") {
      val uberspect = new ScalaUberspect
      uberspect.init()
      uberspect.getIterator(None, dummyInfo) should be (a[java.util.Iterator[_]])
    }
  }

  describe("to VelMethod Spec") {
    it("List to VelMethod") {
      val uberspect = new ScalaUberspect
      uberspect.init()
      uberspect.getMethod(List(1, 2, 3),
        "get",
        Array(new Integer(1)),
        dummyInfo) should be (a[UberspectImpl.VelMethodImpl])
    }

    it("Map to VelMethod") {
      val uberspect = new ScalaUberspect
      uberspect.init()
      uberspect.getMethod(Map("key" -> "value"),
        "get",
        Array("key"),
        dummyInfo) should be (a[RewriteVelMethod])
    }

    it("Map to no VelMethod(not get method)") {
      val uberspect = new ScalaUberspect
      uberspect.init()
      uberspect.getMethod(Map("key" -> "value"),
        "apply",
        Array("key"),
        dummyInfo) should not be (a[RewriteVelMethod])
    }

    it("mutable.ArrayBuffer to VelMethod") {
      val uberspect = new ScalaUberspect
      uberspect.init()
      uberspect.getMethod(mutable.ArrayBuffer(1, 2, 3),
        "get",
        Array(new Integer(1)),
        dummyInfo) should be (a[UberspectImpl.VelMethodImpl])
    }

    it("mutable.Map to VelMethod") {
      val uberspect = new ScalaUberspect
      uberspect.init()
      uberspect.getMethod(mutable.Map("key" -> "value"),
        "get",
        Array("key"),
        dummyInfo) should be (a[RewriteVelMethod])
    }
  }
}
