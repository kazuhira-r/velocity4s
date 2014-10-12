package org.velocity4s.introspection

import scala.collection.JavaConverters._

import java.lang.reflect.Method

import org.velocity4s.parser.node.{ ScalaMapGetExecutor, ScalaOptionGetExecutor, ScalaPropertyExecutor }

import org.apache.velocity.util.introspection.{ Info, UberspectImpl, VelMethod, VelPropertyGet }

object ScalaUberspectSupport {
  private val GET_METHOD_NAME = "get"
}

abstract class ScalaUberspectSupport extends UberspectImpl {
  override def getIterator(obj: Object, i: Info): java.util.Iterator[_] =
    obj match {
      case option: Option[_]               => option.iterator.asJava
      case map: scala.collection.Map[_, _] => map.values.iterator.asJava
      case iterable: Iterable[_]           => iterable.iterator.asJava
      case iterator: Iterator[_]           => iterator.asJava
      case _                               => super.getIterator(obj, i)
    }

  override def getMethod(obj: AnyRef, methodName: String, args: Array[AnyRef], i: Info): VelMethod =
    (obj, methodName) match {
      case (_: Option[_], ScalaUberspectSupport.GET_METHOD_NAME) if args.size == 0 =>
        val method = introspector.getMethod(obj.getClass, methodName, args)

        if (method != null)
          new RewriteVelMethod(method, (o, params) => {
            o.asInstanceOf[Option[AnyRef]].getOrElse(null)
          })
        else
          super.getMethod(obj, methodName, args, i)
      case (_: Seq[_], ScalaUberspectSupport.GET_METHOD_NAME) if args.size == 1 =>
        super.getMethod(obj, "apply", args, i)
      case (_: scala.collection.Map[_, _], ScalaUberspectSupport.GET_METHOD_NAME) if args.size == 1 =>
        val method = introspector.getMethod(obj.getClass, methodName, args)

        if (method != null) {
          new RewriteVelMethod(method, (o, params) => {
            o.asInstanceOf[scala.collection.Map[AnyRef, AnyRef]].getOrElse(params(0), null)
          })
        } else {
          super.getMethod(obj, methodName, args, i)
        }
      case _ =>
        super.getMethod(obj, methodName, args, i)
    }

  override def getPropertyGet(obj: AnyRef, identifier: String, i: Info): VelPropertyGet =
    Option(obj)
      .map {
        case option: Option[_] if identifier == ScalaUberspectSupport.GET_METHOD_NAME =>
          new ScalaOptionGetExecutor(log, introspector, obj.getClass, identifier)
        case map: scala.collection.Map[_, _] =>
          new ScalaMapGetExecutor(log, introspector, obj.getClass, identifier)
        case _ =>
          new ScalaPropertyExecutor(log, introspector, obj.getClass, identifier)
      }.map { executor =>
        if (executor.isAlive) {
          new UberspectImpl.VelGetterImpl(executor)
        } else {
          super.getPropertyGet(obj, identifier, i)
        }
      }.getOrElse(null)
}
