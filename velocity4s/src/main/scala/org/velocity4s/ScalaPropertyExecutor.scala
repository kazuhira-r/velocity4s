package org.velocity4s

import org.apache.velocity.runtime.log.Log
import org.apache.velocity.runtime.parser.node.PropertyExecutor
import org.apache.velocity.util.introspection.Introspector

class ScalaPropertyExecutor(log: Log, introspector: Introspector, clazz: Class[_], property: String)
  extends PropertyExecutor(log, introspector, clazz, property) {

  override protected def discover(clazz: Class[_], property: String): Unit = {
   setMethod(introspector.getMethod(clazz, property, Array.empty[AnyRef]))

    if (!isAlive) {
      super.discover(clazz, property)
    }
  }
}
