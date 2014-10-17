package org.velocity4s.parser.node

import org.apache.velocity.runtime.log.Log
import org.apache.velocity.runtime.parser.node.MapGetExecutor
import org.apache.velocity.util.introspection.Introspector

class ScalaMapGetExecutor(log: Log, introspector: Introspector, clazz: Class[_], property: String)
    extends MapGetExecutor(log, clazz, property) {

  override def isAlive: Boolean = true

  override protected def discover(clazz: Class[_]): Unit =
    setMethod(introspector.getMethod(clazz, property, Array.empty[AnyRef]))

  override def execute(o: AnyRef): AnyRef =
    Option(getMethod)
      .map(_.invoke(o))
      .getOrElse(o.asInstanceOf[scala.collection.Map[String, AnyRef]].getOrElse(property, null))
}
