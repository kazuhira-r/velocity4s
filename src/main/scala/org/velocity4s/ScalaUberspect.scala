package org.velocity4s

import scala.collection.{GenMapLike, GenSeqLike}
import scala.collection.JavaConverters._

import java.lang.reflect.Method

import org.apache.velocity.util.introspection.{Info, UberspectImpl, VelMethod, VelPropertyGet}

object ScalaUberspect {
  private val GET_METHOD_NAME = "get"
}

class ScalaUberspect extends UberspectImpl {
  override def getIterator(obj: Object, i: Info): java.util.Iterator[_] =
    obj match {
      case option: Option[_] => option.iterator.asJava
      case iterable: Iterable[_] => iterable.iterator.asJava
      case iterator: Iterator[_] => iterator.asJava
      case _ => super.getIterator(obj, i)
    }

  override def getMethod(obj: AnyRef, methodName: String, args: Array[AnyRef], i: Info): VelMethod =
    (obj, methodName) match {
      case (_: GenSeqLike[_, _], ScalaUberspect.GET_METHOD_NAME) if args.size == 1 =>
        super.getMethod(obj, "apply", args, i)
      case (_: GenMapLike[_, _, _], ScalaUberspect.GET_METHOD_NAME) if args.size == 1 =>
        val method = introspector.getMethod(obj.getClass, "get", args)
        new MapApplyVelMethod(method)
      case _ =>
        super.getMethod(obj, methodName, args, i)
    }

  override def getPropertyGet(obj: AnyRef, identifier: String, i: Info): VelPropertyGet = {
    Option(obj)
      .map {
        case map: Map[_, _] => new ScalaMapGetExecutor(log, obj.getClass, identifier)
        case _ => new ScalaPropertyExecutor(log, introspector, obj.getClass, identifier)
      }.map { executor =>
        if (executor.isAlive)
          new UberspectImpl.VelGetterImpl(executor)
        else
          super.getPropertyGet(obj, identifier, i)
      }.getOrElse(null)
  }
}

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
