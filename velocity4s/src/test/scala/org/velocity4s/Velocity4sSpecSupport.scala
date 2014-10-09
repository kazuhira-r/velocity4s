package org.velocity4s

import java.io.{ StringReader, StringWriter }
import java.util.UUID

import org.apache.velocity.VelocityContext

import org.scalatest.Suite

trait Velocity4sSpecSupport extends Suite {
  protected def eval(templateAsString: String, params: (String, Any)*): String = {
    val velocity = ScalaVelocityEngine.create
    velocity.init()

    val context = new VelocityContext
    params.foreach { case (k, v) => context.put(k, v) }

    val writer = new StringWriter
    velocity.evaluate(context, writer, getClass.getSimpleName, new StringReader(templateAsString))
    writer.toString
  }
}
