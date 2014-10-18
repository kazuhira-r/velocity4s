package org.velocity4s.context

import org.apache.velocity.context.Context

trait ScalaContextSupport extends Context {
  def keys: Iterable[String] =
    getKeys.toIterable.asInstanceOf[Iterable[String]]

  def +(elem: (String, AnyRef)): this.type = {
    put(elem._1, elem._2)
    this
  }

  def ++(elems: (String, AnyRef)*): this.type =
    this ++ elems

  def ++(elems: Iterable[(String, AnyRef)]): this.type = {
    elems.foreach(this + _)
    this
  }

  def -(key: String): this.type = {
    remove(key)
    this
  }

  def --(keys: String*): this.type =
    this -- keys

  def --(keys: Iterable[String]): this.type = {
    keys.foreach(this - _)
    this
  }
}
