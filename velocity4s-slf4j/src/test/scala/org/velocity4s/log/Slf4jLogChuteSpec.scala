package org.velocity4s.log

import org.apache.velocity.runtime.log.LogChute

import org.slf4j.Logger

import org.scalatest.FunSpec
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._
import org.mockito.internal.util.reflection.Whitebox

class Slf4jLogChuteSpec extends FunSpec with MockitoSugar {
  describe("Slf4jLogChute Spec") {
    it("log message") {
      val mockLogger = mock[Logger]

      val slf4jLogChute = new Slf4jLogChute
      Whitebox.setInternalState(slf4jLogChute, "logger", mockLogger)

      slf4jLogChute.log(LogChute.DEBUG_ID, "debug message")
      slf4jLogChute.log(LogChute.TRACE_ID, "trace message")
      slf4jLogChute.log(LogChute.INFO_ID, "info message")
      slf4jLogChute.log(LogChute.WARN_ID, "warn message")
      slf4jLogChute.log(LogChute.ERROR_ID, "error message")

      verify(mockLogger).debug("debug message")
      verify(mockLogger).trace("trace message")
      verify(mockLogger).info("info message")
      verify(mockLogger).warn("warn message")
      verify(mockLogger).error("error message")
    }

    it("log message with Throwable") {
      val mockLogger = mock[Logger]

      val slf4jLogChute = new Slf4jLogChute
      Whitebox.setInternalState(slf4jLogChute, "logger", mockLogger)

      val th = new Throwable

      slf4jLogChute.log(LogChute.DEBUG_ID, "debug message", th)
      slf4jLogChute.log(LogChute.TRACE_ID, "trace message", th)
      slf4jLogChute.log(LogChute.INFO_ID, "info message", th)
      slf4jLogChute.log(LogChute.WARN_ID, "warn message", th)
      slf4jLogChute.log(LogChute.ERROR_ID, "error message", th)

      verify(mockLogger).debug("debug message", th)
      verify(mockLogger).trace("trace message", th)
      verify(mockLogger).info("info message", th)
      verify(mockLogger).warn("warn message", th)
      verify(mockLogger).error("error message", th)
    }
  }
}
