package org.velocity4s.context

trait ScalaContextSupport extends ScalaContext {
  def keys: Iterable[String] =
    getKeys.toIterable.asInstanceOf[Iterable[String]]

  override def +=(elem: (String, AnyRef), elems: (String, AnyRef)*): this.type = {
    put(elem._1, elem._2)
    elems.foreach(e => put(e._1, e._2))
    this
  }

  override def ++=(elems: Iterable[(String, AnyRef)]): this.type = {
    elems.foreach(this += _)
    this
  }

  override def -=(key: String, keys: String*): this.type = {
    remove(key)
    keys.foreach(k => remove(k))
    this
  }

  override def --=(keys: Iterable[String]): this.type = {
    keys.foreach(this -= _)
    this
  }
}
