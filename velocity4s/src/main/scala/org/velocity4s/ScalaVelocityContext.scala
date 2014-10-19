package org.velocity4s

import scala.collection.JavaConverters._

import org.apache.velocity.VelocityContext
import org.apache.velocity.context.Context

import org.velocity4s.context.ScalaContextSupport

object ScalaVelocityContext {
  def empty: ScalaVelocityContext = apply()

  def apply(context: java.util.Map[String, AnyRef]): ScalaVelocityContext =
    new ScalaVelocityContext(context, null)

  def apply(innerContext: Context): ScalaVelocityContext =
    new ScalaVelocityContext(null, innerContext)

  def apply(elems: (String, AnyRef)*): ScalaVelocityContext = {
    val context = new java.util.HashMap[String, AnyRef](elems.toMap.asJava)
    new ScalaVelocityContext(context, null)
  }
}

class ScalaVelocityContext protected (context: java.util.Map[String, AnyRef], innerContext: Context)
    extends VelocityContext(context, innerContext)
    with ScalaContextSupport {
  protected def this(context: java.util.Map[String, AnyRef]) = this(context, null)

  protected def this(innerContext: Context) = this(null, innerContext)

  override def clone: AnyRef =
    try {
      val c = super.clone.asInstanceOf[ScalaVelocityContext]
      keys.foreach { key => c += (key -> get(key)) }
      c
    } catch {
      case ignored: CloneNotSupportedException =>
        ScalaVelocityContext.empty
    }

  override def toString: String = context.toString
}
