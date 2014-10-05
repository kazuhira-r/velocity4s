package org.velocity4s

import org.apache.velocity.app.VelocityEngine
import org.apache.velocity.runtime.RuntimeConstants

object ScalaVelocityEngine {
  def create: ScalaVelocityEngine =
    new ScalaSimplyVelocityEngine

  def create(properties: java.util.Properties): ScalaVelocityEngine = {
    properties.getProperty(RuntimeConstants.UBERSPECT_CLASSNAME) match {
      case null =>
        val copy = new java.util.Properties(properties)
        copy.setProperty(RuntimeConstants.UBERSPECT_CLASSNAME, classOf[ScalaUberspect].getName)
        new ScalaPropertiesVelocityEngine(copy)
      case uberspectClassNames if uberspectClassNames.contains(classOf[ScalaUberspect].getName) =>
        new ScalaPropertiesVelocityEngine(properties)
      case uberspectClassNames =>
        val engine = new ScalaPropertiesVelocityEngine(properties)
        engine.addProperty(RuntimeConstants.UBERSPECT_CLASSNAME, classOf[ScalaUberspect].getName)
        engine
    }
  }

  def create(propertiesFileName: String): ScalaVelocityEngine =
    new ScalaPropertiesFileNameVelocityEngine(propertiesFileName)
}

trait ScalaVelocityEngine extends VelocityEngine

private[velocity4s] class ScalaSimplyVelocityEngine extends VelocityEngine
    with ScalaVelocityEngine {
  addProperty(RuntimeConstants.UBERSPECT_CLASSNAME, classOf[ScalaUberspect].getName)
}

private[velocity4s] class ScalaPropertiesVelocityEngine(p: java.util.Properties) extends VelocityEngine(p)
  with ScalaVelocityEngine

private[velocity4s] class ScalaPropertiesFileNameVelocityEngine(propsFilename: String) extends VelocityEngine(propsFilename)
    with ScalaVelocityEngine {
  addProperty(RuntimeConstants.UBERSPECT_CLASSNAME, classOf[ScalaUberspect].getName)
}
