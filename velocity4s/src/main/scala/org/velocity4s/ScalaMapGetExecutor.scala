package org.velocity4s

import scala.collection.GenMapLike

import org.apache.velocity.runtime.log.Log
import org.apache.velocity.runtime.parser.node.MapGetExecutor
import org.apache.velocity.util.introspection.Introspector

class ScalaMapGetExecutor(log: Log, clazz: Class[_], property: String)
  extends MapGetExecutor(log, clazz, property) {
  override def isAlive: Boolean =
    true

  override def execute(o: AnyRef): AnyRef = {
    o.asInstanceOf[GenMapLike[String, AnyRef, _]].getOrElse(property, null)
  }
}
