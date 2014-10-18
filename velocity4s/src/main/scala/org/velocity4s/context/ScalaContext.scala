package org.velocity4s.context

import org.apache.velocity.context.Context

trait ScalaContext extends Context {
  def keys: Iterable[String]

  def +(elem: (String, AnyRef)): this.type

  def ++(elems: (String, AnyRef)*): this.type

  def ++(elems: Iterable[(String, AnyRef)]): this.type

  def -(key: String): this.type

  def --(keys: String*): this.type

  def --(keys: Iterable[String]): this.type
}
