package org.velocity4s

import org.scalatest.FunSpec
import org.scalatest.Matchers._

class PropertiesLoaderSpec extends FunSpec {
  describe("Load properites file, from Classpath") {
    it("properties file, found from classpath") {
      val properties = PropertiesLoader.load("test.properties")
      properties should not be (empty)
      properties.get.getProperty("key") should be ("value")
    }

    it("properties file, not found from classpath") {
      val properties = PropertiesLoader.load("notfound.properties")
      properties should be (empty)
    }
  }
}
