package org.velocity4s

import org.apache.velocity.app.VelocityEngine
import org.apache.velocity.runtime.RuntimeConstants

object ScalaVelocityEngine {
  def create: ScalaVelocityEngine =
    new ScalaSimplyVelocityEngine

  def create(properties: java.util.Properties): ScalaVelocityEngine =
    new ScalaPropertiesVelocityEngine(properties)

  def create(propertiesFileName: String): ScalaVelocityEngine =
    new ScalaPropertiesFileNameVelocityEngine(propertiesFileName)
}

trait ScalaVelocityEngine extends VelocityEngine

private[velocity4s] class ScalaSimplyVelocityEngine extends VelocityEngine
    with ScalaVelocityEngine {
  addProperty(RuntimeConstants.UBERSPECT_CLASSNAME, classOf[ScalaUberspect].getName)
}

private[velocity4s] class ScalaPropertiesVelocityEngine(p: java.util.Properties) extends VelocityEngine(p)
    with ScalaVelocityEngine {

  p.getProperty(RuntimeConstants.UBERSPECT_CLASSNAME) match {
    case null =>
      addProperty(RuntimeConstants.UBERSPECT_CLASSNAME, classOf[ScalaUberspect].getName)
    case uberspectClassNames if uberspectClassNames.contains(classOf[ScalaUberspect].getName) =>
    case uberspectClassNames =>
      addProperty(RuntimeConstants.UBERSPECT_CLASSNAME, classOf[ScalaUberspect].getName)
  }
}

private[velocity4s] class ScalaPropertiesFileNameVelocityEngine(propsFilename: String) extends VelocityEngine(propsFilename)
    with ScalaVelocityEngine {
  addProperty(RuntimeConstants.UBERSPECT_CLASSNAME, classOf[ScalaUberspect].getName)
}
