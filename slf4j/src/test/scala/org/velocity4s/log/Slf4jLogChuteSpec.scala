package org.velocity4s.log

import org.apache.velocity.runtime.log.LogChute

import org.slf4j.Logger

import org.scalatest.FunSpec
import org.scalatest.Matchers._
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._
import org.mockito.internal.util.reflection.Whitebox

class Slf4jLogChuteSpec extends FunSpec with MockitoSugar {
  describe("Slf4jLogChute Spec") {
    it("debug enabled") {
      val mockLogger = mock[Logger]

      val slf4jLogChute = new Slf4jLogChute
      Whitebox.setInternalState(slf4jLogChute, "logger", mockLogger)

      when(mockLogger.isDebugEnabled).thenReturn(true)
      when(mockLogger.isTraceEnabled).thenReturn(false)
      when(mockLogger.isInfoEnabled).thenReturn(false)
      when(mockLogger.isWarnEnabled).thenReturn(false)
      when(mockLogger.isErrorEnabled).thenReturn(false)

      slf4jLogChute.isLevelEnabled(LogChute.DEBUG_ID) should be (true)
    }

    it("trace enabled") {
      val mockLogger = mock[Logger]

      val slf4jLogChute = new Slf4jLogChute
      Whitebox.setInternalState(slf4jLogChute, "logger", mockLogger)

      when(mockLogger.isDebugEnabled).thenReturn(false)
      when(mockLogger.isTraceEnabled).thenReturn(true)
      when(mockLogger.isInfoEnabled).thenReturn(false)
      when(mockLogger.isWarnEnabled).thenReturn(false)
      when(mockLogger.isErrorEnabled).thenReturn(false)

      slf4jLogChute.isLevelEnabled(LogChute.TRACE_ID) should be (true)
    }

    it("info enabled") {
      val mockLogger = mock[Logger]

      val slf4jLogChute = new Slf4jLogChute
      Whitebox.setInternalState(slf4jLogChute, "logger", mockLogger)

      when(mockLogger.isDebugEnabled).thenReturn(false)
      when(mockLogger.isTraceEnabled).thenReturn(false)
      when(mockLogger.isInfoEnabled).thenReturn(true)
      when(mockLogger.isWarnEnabled).thenReturn(false)
      when(mockLogger.isErrorEnabled).thenReturn(false)

      slf4jLogChute.isLevelEnabled(LogChute.INFO_ID) should be (true)
    }

    it("warn enabled") {
      val mockLogger = mock[Logger]

      val slf4jLogChute = new Slf4jLogChute
      Whitebox.setInternalState(slf4jLogChute, "logger", mockLogger)

      when(mockLogger.isDebugEnabled).thenReturn(false)
      when(mockLogger.isTraceEnabled).thenReturn(false)
      when(mockLogger.isInfoEnabled).thenReturn(false)
      when(mockLogger.isWarnEnabled).thenReturn(true)
      when(mockLogger.isErrorEnabled).thenReturn(false)

      slf4jLogChute.isLevelEnabled(LogChute.WARN_ID) should be (true)
    }

    it("error enabled") {
      val mockLogger = mock[Logger]

      val slf4jLogChute = new Slf4jLogChute
      Whitebox.setInternalState(slf4jLogChute, "logger", mockLogger)

      when(mockLogger.isDebugEnabled).thenReturn(false)
      when(mockLogger.isTraceEnabled).thenReturn(false)
      when(mockLogger.isInfoEnabled).thenReturn(false)
      when(mockLogger.isWarnEnabled).thenReturn(false)
      when(mockLogger.isErrorEnabled).thenReturn(true)

      slf4jLogChute.isLevelEnabled(LogChute.ERROR_ID) should be (true)
    }

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
