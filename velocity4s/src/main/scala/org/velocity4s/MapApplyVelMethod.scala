package org.velocity4s

import java.lang.reflect.Method

import org.apache.velocity.util.introspection.VelMethod


private[velocity4s] class MapApplyVelMethod(method: Method) extends VelMethod {
  override def getMethodName: String =
    method.getName

  override def getReturnType: Class[_] =
    method.getReturnType

  override def isCacheable: Boolean =
    true

  override def invoke(o: AnyRef, params: Array[AnyRef]): AnyRef = {
    method.invoke(o, params(0)) match {
      case None => null
      case Some(v) => v.asInstanceOf[AnyRef]
      case r => r
    }
  }
}
