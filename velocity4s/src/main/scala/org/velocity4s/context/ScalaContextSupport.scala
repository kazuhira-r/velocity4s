package org.velocity4s.context

import org.apache.velocity.context.Context

trait ScalaContextSupport extends Context {
  def keys: Iterable[String] =
    getKeys.toIterable.asInstanceOf[Iterable[String]]

  def +=(elem: (String, AnyRef), elems: (String, AnyRef)*): this.type = {
    put(elem._1, elem._2)
    elems.foreach(e => put(e._1, e._2))
    this
  }

  def ++=(elems: Iterable[(String, AnyRef)]): this.type = {
    elems.foreach(this += _)
    this
  }

  def -=(key: String, keys: String*): this.type = {
    remove(key)
    keys.foreach(k => remove(k))
    this
  }

  def --=(keys: Iterable[String]): this.type = {
    keys.foreach(this -= _)
    this
  }
}
