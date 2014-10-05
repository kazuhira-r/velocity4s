package org.velocity4s

import java.io.StringWriter
import java.util.UUID

import org.apache.velocity.{ Template, VelocityContext }
import org.apache.velocity.context.Context
import org.apache.velocity.runtime.RuntimeConstants
import org.apache.velocity.runtime.resource.loader.StringResourceLoader

import org.scalatest.Suite

trait Velocity4sSpecSupport extends Suite {
  protected def newEngineWithTemplate(templateAsString: String): (ScalaVelocityEngine, String) = {
    val engine = ScalaVelocityEngine.create
    engine.addProperty(RuntimeConstants.RESOURCE_LOADER, "string")
    engine.addProperty("string.resource.loader.class", classOf[StringResourceLoader].getName)
    engine.init()

    val templateName = s"${UUID.randomUUID}.vm"
    StringResourceLoader.getRepository.putStringResource(templateName, templateAsString)
    (engine, templateName)
  }

  protected def newContext(pairs: (String, Any)*): Context = {
    val context = new VelocityContext
    pairs.foreach { case (k, v) => context.put(k, v) }
    context
  }

  protected def merge(template: Template, context: Context): String = {
    val writer = new StringWriter
    template.merge(context, writer)
    writer.toString
  }
}
