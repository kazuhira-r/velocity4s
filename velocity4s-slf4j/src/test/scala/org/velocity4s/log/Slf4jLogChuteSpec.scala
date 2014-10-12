package org.velocity4s.log

import org.apache.velocity.runtime.log.LogChute

import org.slf4j.Logger

import org.scalamock.scalatest.MockFactory
import org.scalatest.FunSpec
import org.scalatest.Matchers._

class Slf4jLogChuteSpec extends FunSpec with MockFactory {
  describe("Slf4jLogChute Spec") {
    it("log message") {
      val mockLogger = mock[Logger]

      val slf4jLogChute = new Slf4jLogChute {
        logger = mockLogger
      }

      (mockLogger.debug(_: String)).expects("debug message")
      (mockLogger.trace(_: String)).expects("trace message")
      (mockLogger.info(_: String)).expects("info message")
      (mockLogger.warn(_: String)).expects("warn message")
      (mockLogger.error(_: String)).expects("error message")

      slf4jLogChute.log(LogChute.DEBUG_ID, "debug message")
      slf4jLogChute.log(LogChute.TRACE_ID, "trace message")
      slf4jLogChute.log(LogChute.INFO_ID, "info message")
      slf4jLogChute.log(LogChute.WARN_ID, "warn message")
      slf4jLogChute.log(LogChute.ERROR_ID, "error message")
    }

    it("log message with Throwable") {
      val mockLogger = mock[Logger]

      val slf4jLogChute = new Slf4jLogChute {
        logger = mockLogger
      }

      val th = new Throwable

      (mockLogger.debug(_: String, _: Throwable)).expects("debug message", th)
      (mockLogger.trace(_: String, _: Throwable)).expects("trace message", th)
      (mockLogger.info(_: String, _: Throwable)).expects("info message", th)
      (mockLogger.warn(_: String, _: Throwable)).expects("warn message", th)
      (mockLogger.error(_: String, _: Throwable)).expects("error message", th)

      slf4jLogChute.log(LogChute.DEBUG_ID, "debug message", th)
      slf4jLogChute.log(LogChute.TRACE_ID, "trace message", th)
      slf4jLogChute.log(LogChute.INFO_ID, "info message", th)
      slf4jLogChute.log(LogChute.WARN_ID, "warn message", th)
      slf4jLogChute.log(LogChute.ERROR_ID, "error message", th)
    }
  }
}
