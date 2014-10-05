package org.velocity4s

import java.lang.reflect.Method

import org.apache.velocity.util.introspection.VelMethod

class RewriteVelMethod(method: Method, fun: (AnyRef, Array[AnyRef]) => AnyRef) extends VelMethod {
  override def getMethodName: String =
    method.getName

  override def getReturnType: Class[_] =
    method.getReturnType

  override def isCacheable: Boolean =
    true

  override def invoke(o: AnyRef, params: Array[AnyRef]): AnyRef =
    fun(o, params)
}
