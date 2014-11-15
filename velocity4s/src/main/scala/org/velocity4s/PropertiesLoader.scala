package org.velocity4s

private[velocity4s] object PropertiesLoader {
  def load(propertiesFileName: String): Option[java.util.Properties] = {
    val classLoaders =
      Array(getClass.getClassLoader,
        Thread.currentThread.getContextClassLoader)

    val streams =
      classLoaders
        .map(cl => cl.getResourceAsStream(propertiesFileName))

    try {
      streams
        .find(is => is != null)
        .map { is =>
          val properties = new java.util.Properties
          properties.load(is)
          properties
        }
    } finally {
      streams.withFilter(_ != null).foreach(_.close())
    }
  }
}
