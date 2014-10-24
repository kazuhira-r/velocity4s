package org.velocity4s.log

import org.apache.velocity.runtime.log.LogChute
import org.apache.logging.log4j.Logger

import org.scalatest.FunSpec
import org.scalatest.Matchers._
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._
import org.mockito.internal.util.reflection.Whitebox

class Log4j2LogChuteSpec extends FunSpec with MockitoSugar {
  describe("Log4j2LogChute Spec") {
    it("debug enabled") {
      val mockLogger = mock[Logger]

      val log4j2LogChute = new Log4j2LogChute
      Whitebox.setInternalState(log4j2LogChute, "logger", mockLogger)

      when(mockLogger.isDebugEnabled).thenReturn(true)
      when(mockLogger.isTraceEnabled).thenReturn(false)
      when(mockLogger.isInfoEnabled).thenReturn(false)
      when(mockLogger.isWarnEnabled).thenReturn(false)
      when(mockLogger.isErrorEnabled).thenReturn(false)

      log4j2LogChute.isLevelEnabled(LogChute.DEBUG_ID) should be (true)
    }

    it("trace enabled") {
      val mockLogger = mock[Logger]

      val log4j2LogChute = new Log4j2LogChute
      Whitebox.setInternalState(log4j2LogChute, "logger", mockLogger)

      when(mockLogger.isDebugEnabled).thenReturn(false)
      when(mockLogger.isTraceEnabled).thenReturn(true)
      when(mockLogger.isInfoEnabled).thenReturn(false)
      when(mockLogger.isWarnEnabled).thenReturn(false)
      when(mockLogger.isErrorEnabled).thenReturn(false)

      log4j2LogChute.isLevelEnabled(LogChute.TRACE_ID) should be (true)
    }

    it("info enabled") {
      val mockLogger = mock[Logger]

      val log4j2LogChute = new Log4j2LogChute
      Whitebox.setInternalState(log4j2LogChute, "logger", mockLogger)

      when(mockLogger.isDebugEnabled).thenReturn(false)
      when(mockLogger.isTraceEnabled).thenReturn(false)
      when(mockLogger.isInfoEnabled).thenReturn(true)
      when(mockLogger.isWarnEnabled).thenReturn(false)
      when(mockLogger.isErrorEnabled).thenReturn(false)

      log4j2LogChute.isLevelEnabled(LogChute.INFO_ID) should be (true)
    }

    it("warn enabled") {
      val mockLogger = mock[Logger]

      val log4j2LogChute = new Log4j2LogChute
      Whitebox.setInternalState(log4j2LogChute, "logger", mockLogger)

      when(mockLogger.isDebugEnabled).thenReturn(false)
      when(mockLogger.isTraceEnabled).thenReturn(false)
      when(mockLogger.isInfoEnabled).thenReturn(false)
      when(mockLogger.isWarnEnabled).thenReturn(true)
      when(mockLogger.isErrorEnabled).thenReturn(false)

      log4j2LogChute.isLevelEnabled(LogChute.WARN_ID) should be (true)
    }

    it("error enabled") {
      val mockLogger = mock[Logger]

      val log4j2LogChute = new Log4j2LogChute
      Whitebox.setInternalState(log4j2LogChute, "logger", mockLogger)

      when(mockLogger.isDebugEnabled).thenReturn(false)
      when(mockLogger.isTraceEnabled).thenReturn(false)
      when(mockLogger.isInfoEnabled).thenReturn(false)
      when(mockLogger.isWarnEnabled).thenReturn(false)
      when(mockLogger.isErrorEnabled).thenReturn(true)

      log4j2LogChute.isLevelEnabled(LogChute.ERROR_ID) should be (true)
    }

    it("log message") {
      val mockLogger = mock[Logger]

      val log4j2LogChute = new Log4j2LogChute
      Whitebox.setInternalState(log4j2LogChute, "logger", mockLogger)

      log4j2LogChute.log(LogChute.DEBUG_ID, "debug message")
      log4j2LogChute.log(LogChute.TRACE_ID, "trace message")
      log4j2LogChute.log(LogChute.INFO_ID, "info message")
      log4j2LogChute.log(LogChute.WARN_ID, "warn message")
      log4j2LogChute.log(LogChute.ERROR_ID, "error message")

      verify(mockLogger).debug("debug message")
      verify(mockLogger).trace("trace message")
      verify(mockLogger).info("info message")
      verify(mockLogger).warn("warn message")
      verify(mockLogger).error("error message")
    }

    it("log message with Throwable") {
      val mockLogger = mock[Logger]

      val log4j2LogChute = new Log4j2LogChute
      Whitebox.setInternalState(log4j2LogChute, "logger", mockLogger)

      val th = new Throwable

      log4j2LogChute.log(LogChute.DEBUG_ID, "debug message", th)
      log4j2LogChute.log(LogChute.TRACE_ID, "trace message", th)
      log4j2LogChute.log(LogChute.INFO_ID, "info message", th)
      log4j2LogChute.log(LogChute.WARN_ID, "warn message", th)
      log4j2LogChute.log(LogChute.ERROR_ID, "error message", th)

      verify(mockLogger).debug("debug message", th)
      verify(mockLogger).trace("trace message", th)
      verify(mockLogger).info("info message", th)
      verify(mockLogger).warn("warn message", th)
      verify(mockLogger).error("error message", th)
    }
  }
}
