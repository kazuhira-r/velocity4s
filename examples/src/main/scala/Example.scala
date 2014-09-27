import org.velocity4s.ScalaVelocityEngine

import java.io.StringWriter

import org.apache.velocity.VelocityContext
import org.apache.velocity.app.VelocityEngine
import org.apache.velocity.runtime.RuntimeConstants
import org.apache.velocity.runtime.resource.loader.StringResourceLoader

object Example {
  def main(args: Array[String]): Unit = {
    usage1()
    usage2()
  }

  def usage1(): Unit = {
    case class Person(name: String, age: Int)

    val engine = ScalaVelocityEngine.create
    engine.addProperty(RuntimeConstants.RESOURCE_LOADER, "string")
    engine.addProperty("string.resource.loader.class", classOf[StringResourceLoader].getName)
    engine.init()

    val templateAsString = """|$person.name
                              |$person.age
                              |
                              |#foreach ($e in $list)
                              |$e
                              |#end
                              |
                              |#foreach ($k in $map.keys())
                              |$map[$k]
                              |#end
                              |
                              |#foreach ($v in $some)
                              |some($v)
                              |#end
                              |#foreach ($v in $none)
                              |none
                              |#end
                              |
                              |""".stripMargin
    val templateName = "template.vm"

    StringResourceLoader.getRepository.putStringResource(templateName, templateAsString)

    val context = new VelocityContext
    context.put("person", Person("Taro", 20))
    context.put("list", List("Java", "Scala", "Groovy", "Clojure"))
    context.put("map", Map("key1" -> "value1", "key2" -> "value2"))
    context.put("some", Some("hello"))
    context.put("none", None)

    val template = engine.getTemplate(templateName)

    val writer = new StringWriter
    template.merge(context, writer)
    println(writer)
  }

  def usage2(): Unit = {
    val engine = new VelocityEngine("examples/src/main/resources/velocity.properties")
    engine.addProperty(RuntimeConstants.RESOURCE_LOADER, "string")
    engine.addProperty("string.resource.loader.class", classOf[StringResourceLoader].getName)
    engine.init()

    val templateAsString = """|#foreach ($e in $list)
                              |$e
                              |#end""".stripMargin
    val templateName = "template.vm"

    StringResourceLoader.getRepository.putStringResource(templateName, templateAsString)

    val context = new VelocityContext
    context.put("list", List("Java", "Scala", "Groovy", "Clojure"))

    val template = engine.getTemplate(templateName)

    val writer = new StringWriter
    template.merge(context, writer)
    println(writer)
  }
}
