package org.velocity4s

import scala.collection.JavaConverters._

import java.io.{ StringReader, StringWriter }

import org.apache.velocity.VelocityContext
import org.apache.velocity.runtime.RuntimeConstants
import org.apache.velocity.runtime.log.NullLogChute

import org.scalatest.FunSpec
import org.scalatest.Matchers._

class ScalaVelocityEngineSpec extends FunSpec {
  describe(s"Add ScalaUberspect Spec") {
    it(s"${classOf[ScalaSimplyVelocityEngine].getSimpleName}, create") {
      val velocity = ScalaVelocityEngine.create
      velocity.init()
      velocity.getProperty(RuntimeConstants.UBERSPECT_CLASSNAME) should be (classOf[ScalaUberspect].getName)
    }

    it(s"${classOf[ScalaSimplyVelocityEngine].getSimpleName}, build") {
      val velocity = ScalaVelocityEngine.build
      velocity.getProperty(RuntimeConstants.UBERSPECT_CLASSNAME) should be (classOf[ScalaUberspect].getName)

      val context = new VelocityContext
      context.put("param", "value")

      val writer = new StringWriter
      velocity.evaluate(context, writer, getClass.getSimpleName, new StringReader("$param"))
      writer.toString should be ("value")
    }

    it(s"${classOf[ScalaSimplyVelocityEngine].getSimpleName}, build with properties") {
      val velocity =
        ScalaVelocityEngine.build(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS -> classOf[NullLogChute].getName)
      velocity.getProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS) should be (classOf[NullLogChute].getName)
      velocity.getProperty(RuntimeConstants.UBERSPECT_CLASSNAME) should be (classOf[ScalaUberspect].getName)

      val context = new VelocityContext
      context.put("param", "value")

      val writer = new StringWriter
      velocity.evaluate(context, writer, getClass.getSimpleName, new StringReader("$param"))
      writer.toString should be ("value")
    }

    it(s"${classOf[ScalaPropertiesVelocityEngine].getSimpleName}, create") {
      val is = getClass.getClassLoader.getResourceAsStream(RuntimeConstants.DEFAULT_RUNTIME_PROPERTIES)
      val props = new java.util.Properties
      props.load(is)
      is.close()
      val velocity = ScalaVelocityEngine.create(props)
      velocity.init()
      velocity
        .getProperty(RuntimeConstants.UBERSPECT_CLASSNAME)
        .asInstanceOf[java.util.List[String]]
        .asScala should contain (classOf[ScalaUberspect].getName)
    }

    it(s"${classOf[ScalaPropertiesFileNameVelocityEngine].getSimpleName}, create") {
      val velocity =
        ScalaVelocityEngine.create("velocity4s/src/test/resources/org/apache/velocity/runtime/defaults/velocity4s.properties")
      velocity.init()
      velocity
        .getProperty(RuntimeConstants.UBERSPECT_CLASSNAME)
        .asInstanceOf[java.util.List[String]]
        .asScala should contain (classOf[ScalaUberspect].getName)
    }

    it(s"${classOf[ScalaPropertiesVelocityEngine].getSimpleName}, properties file, load form filesystem") {
      val velocity =
        ScalaVelocityEngine.create("velocity4s/src/test/resources/org/apache/velocity/runtime/defaults/velocity4s.properties")
      velocity.init()
      velocity
        .getProperty(RuntimeConstants.UBERSPECT_CLASSNAME)
        .asInstanceOf[java.util.List[String]]
        .asScala should contain (classOf[ScalaUberspect].getName)
    }

    it(s"${classOf[ScalaPropertiesFileNameVelocityEngine].getSimpleName}, properties file, load form classpath") {
      val velocity =
        ScalaVelocityEngine.create("org/apache/velocity/runtime/defaults/velocity4s.properties")
      velocity.init()
      velocity
        .getProperty(RuntimeConstants.UBERSPECT_CLASSNAME)
        .asInstanceOf[java.util.List[String]]
        .asScala should contain (classOf[ScalaUberspect].getName)
    }

    it(s"${classOf[ScalaPropertiesFileNameVelocityEngine].getSimpleName}, build") {
      val velocity =
        ScalaVelocityEngine.build("velocity4s/src/test/resources/org/apache/velocity/runtime/defaults/velocity4s.properties")
      velocity.init()
      velocity
        .getProperty(RuntimeConstants.UBERSPECT_CLASSNAME)
        .asInstanceOf[java.util.List[String]]
        .asScala should contain (classOf[ScalaUberspect].getName)
    }
  }

  it(s"${classOf[ScalaPropertiesVelocityEngine].getSimpleName}, build with properties") {
    val velocity =
      ScalaVelocityEngine.build("velocity4s/src/test/resources/org/apache/velocity/runtime/defaults/velocity4s.properties",
        RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS -> classOf[NullLogChute].getName)
    velocity.getProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS) should be (classOf[NullLogChute].getName)
    velocity
      .getProperty(RuntimeConstants.UBERSPECT_CLASSNAME)
      .asInstanceOf[java.util.List[String]]
      .asScala should contain (classOf[ScalaUberspect].getName)

    val context = new VelocityContext
    context.put("param", "value")

    val writer = new StringWriter
    velocity.evaluate(context, writer, getClass.getSimpleName, new StringReader("$param"))
    writer.toString should be ("value")
  }
}
