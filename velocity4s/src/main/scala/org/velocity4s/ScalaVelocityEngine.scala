package org.velocity4s

import scala.collection.JavaConverters._

import org.apache.velocity.app.VelocityEngine
import org.apache.velocity.runtime.RuntimeConstants

object ScalaVelocityEngine {
  def create: ScalaVelocityEngine =
    new ScalaSimplyVelocityEngine

  def create(properties: java.util.Properties): ScalaVelocityEngine =
    new ScalaPropertiesVelocityEngine(properties)

  def create(propertiesFileName: String): ScalaVelocityEngine =
    PropertiesLoader.load(propertiesFileName) match {
      case Some(properties) => new ScalaPropertiesVelocityEngine(properties)
      case None             => new ScalaPropertiesFileNameVelocityEngine(propertiesFileName)
    }

  def build: ScalaVelocityEngine = {
    val velocity = create
    velocity.init()
    velocity
  }

  def build(properties: (String, Any)*): ScalaVelocityEngine = {
    val velocity = create

    properties.foreach { case (k, v) => velocity.setProperty(k, v) }

    velocity.init()
    velocity
  }

  def build(propertiesFileName: String, properties: (String, Any)*): ScalaVelocityEngine = {
    val velocity = create(propertiesFileName)

    properties.foreach { case (k, v) => velocity.setProperty(k, v) }

    velocity.init()
    velocity
  }
}

trait ScalaVelocityEngine extends VelocityEngine {
  prependScalaUberspectIfNeeded()

  protected def prependScalaUberspectIfNeeded(): Unit =
    getProperty(RuntimeConstants.UBERSPECT_CLASSNAME) match {
      case null =>
        setProperty(RuntimeConstants.UBERSPECT_CLASSNAME, classOf[ScalaUberspect].getName)
      case name: String if name == classOf[ScalaUberspect].getName =>
      // NOOP
      case name: String =>
        // prepend ScalaUberspect
        setProperty(RuntimeConstants.UBERSPECT_CLASSNAME, classOf[ScalaUberspect].getName)
        addProperty(RuntimeConstants.UBERSPECT_CLASSNAME, name)
      case uberspectClassNames: java.util.List[_] if uberspectClassNames.asScala.contains(classOf[ScalaUberspect].getName.asInstanceOf[Any]) =>
      // NOOP
      case uberspectClassNames: java.util.List[_] =>
        // prepend ScalaUberspect
        setProperty(RuntimeConstants.UBERSPECT_CLASSNAME, classOf[ScalaUberspect].getName)
        uberspectClassNames.asScala.foreach(name => addProperty(RuntimeConstants.UBERSPECT_CLASSNAME, name))
    }
}

private[velocity4s] class ScalaSimplyVelocityEngine extends VelocityEngine
  with ScalaVelocityEngine

private[velocity4s] class ScalaPropertiesVelocityEngine(p: java.util.Properties) extends VelocityEngine(p)
  with ScalaVelocityEngine

private[velocity4s] class ScalaPropertiesFileNameVelocityEngine(propsFilename: String) extends VelocityEngine(propsFilename)
  with ScalaVelocityEngine
