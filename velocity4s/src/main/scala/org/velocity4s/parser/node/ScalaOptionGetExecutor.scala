package org.velocity4s.parser.node

import org.apache.velocity.runtime.log.Log
import org.apache.velocity.runtime.parser.node.PropertyExecutor
import org.apache.velocity.util.introspection.Introspector

class ScalaOptionGetExecutor(log: Log, introspector: Introspector, clazz: Class[_], property: String)
    extends PropertyExecutor(log, introspector, clazz, property) {

  override protected def discover(clazz: Class[_], property: String): Unit = {
    setMethod(introspector.getMethod(clazz, property, Array.empty[AnyRef]))

    if (!isAlive) {
      super.discover(clazz, property)
    }
  }

  override def execute(o: AnyRef): AnyRef =
    if (isAlive) {
      o.asInstanceOf[Option[AnyRef]].getOrElse(null)
    } else {
      null
    }
}
