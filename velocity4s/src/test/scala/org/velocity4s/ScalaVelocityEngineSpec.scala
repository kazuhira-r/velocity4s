package org.velocity4s

import scala.collection.JavaConverters._

import org.apache.velocity.runtime.RuntimeConstants

import org.scalatest.FunSpec
import org.scalatest.Matchers._

class ScalaVelocityEngineSpec extends FunSpec {
  describe(s"Add ${classOf[ScalaUberspect]} Spec") {
    it(classOf[ScalaSimplyVelocityEngine].getSimpleName) {
      val engine = ScalaVelocityEngine.create
      engine.init()
      engine.getProperty(RuntimeConstants.UBERSPECT_CLASSNAME) should be (classOf[ScalaUberspect].getName)
    }

    it(classOf[ScalaPropertiesVelocityEngine].getSimpleName) {
      val is = getClass.getClassLoader.getResourceAsStream(RuntimeConstants.DEFAULT_RUNTIME_PROPERTIES)
      val props = new java.util.Properties
      props.load(is)
      is.close()
      val engine = ScalaVelocityEngine.create(props)
      engine.init()
      engine
        .getProperty(RuntimeConstants.UBERSPECT_CLASSNAME)
        .asInstanceOf[java.util.Vector[String]]
        .asScala should contain (classOf[ScalaUberspect].getName)
    }

    it(classOf[ScalaPropertiesFileNameVelocityEngine].getSimpleName) {
      val engine =
        ScalaVelocityEngine.create("velocity4s/src/test/resources/org/apache/velocity/runtime/defaults/velocity4s.properties")
      engine.init()
      engine
        .getProperty(RuntimeConstants.UBERSPECT_CLASSNAME)
        .asInstanceOf[java.util.Vector[String]]
        .asScala should contain (classOf[ScalaUberspect].getName)
    }
  }
}
