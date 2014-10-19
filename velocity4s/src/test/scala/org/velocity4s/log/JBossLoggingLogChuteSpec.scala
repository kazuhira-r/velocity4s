package org.velocity4s.log

import org.apache.velocity.runtime.log.LogChute
import org.jboss.logging.Logger
import org.jboss.logging.Logger.Level

import org.scalatest.FunSpec
import org.scalatest.Matchers._
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._
import org.mockito.internal.util.reflection.Whitebox

class JBossLoggingLogChuteSpec extends FunSpec with MockitoSugar {
  describe("JBossLoggingLogChute Spec") {
    it("debug enabled") {
      val mockLogger = mock[Logger]

      val jbossLoggingLogChute = new JBossLoggingLogChute
      Whitebox.setInternalState(jbossLoggingLogChute, "logger", mockLogger)

      when(mockLogger.isEnabled(Level.DEBUG)).thenReturn(true)
      when(mockLogger.isEnabled(Level.TRACE)).thenReturn(false)
      when(mockLogger.isEnabled(Level.INFO)).thenReturn(false)
      when(mockLogger.isEnabled(Level.WARN)).thenReturn(false)
      when(mockLogger.isEnabled(Level.ERROR)).thenReturn(false)

      jbossLoggingLogChute.isLevelEnabled(LogChute.DEBUG_ID) should be (true)
    }

    it("trace enabled") {
      val mockLogger = mock[Logger]

      val jbossLoggingLogChute = new JBossLoggingLogChute
      Whitebox.setInternalState(jbossLoggingLogChute, "logger", mockLogger)

      when(mockLogger.isEnabled(Level.DEBUG)).thenReturn(false)
      when(mockLogger.isEnabled(Level.TRACE)).thenReturn(true)
      when(mockLogger.isEnabled(Level.INFO)).thenReturn(false)
      when(mockLogger.isEnabled(Level.WARN)).thenReturn(false)
      when(mockLogger.isEnabled(Level.ERROR)).thenReturn(false)

      jbossLoggingLogChute.isLevelEnabled(LogChute.TRACE_ID) should be (true)
    }

    it("info enabled") {
      val mockLogger = mock[Logger]

      val jbossLoggingLogChute = new JBossLoggingLogChute
      Whitebox.setInternalState(jbossLoggingLogChute, "logger", mockLogger)

      when(mockLogger.isEnabled(Level.DEBUG)).thenReturn(false)
      when(mockLogger.isEnabled(Level.TRACE)).thenReturn(false)
      when(mockLogger.isEnabled(Level.INFO)).thenReturn(true)
      when(mockLogger.isEnabled(Level.WARN)).thenReturn(false)
      when(mockLogger.isEnabled(Level.ERROR)).thenReturn(false)

      jbossLoggingLogChute.isLevelEnabled(LogChute.INFO_ID) should be (true)
    }

    it("warn enabled") {
      val mockLogger = mock[Logger]

      val jbossLoggingLogChute = new JBossLoggingLogChute
      Whitebox.setInternalState(jbossLoggingLogChute, "logger", mockLogger)

      when(mockLogger.isEnabled(Level.DEBUG)).thenReturn(false)
      when(mockLogger.isEnabled(Level.TRACE)).thenReturn(false)
      when(mockLogger.isEnabled(Level.INFO)).thenReturn(false)
      when(mockLogger.isEnabled(Level.WARN)).thenReturn(true)
      when(mockLogger.isEnabled(Level.ERROR)).thenReturn(false)

      jbossLoggingLogChute.isLevelEnabled(LogChute.WARN_ID) should be (true)
    }

    it("error enabled") {
      val mockLogger = mock[Logger]

      val jbossLoggingLogChute = new JBossLoggingLogChute
      Whitebox.setInternalState(jbossLoggingLogChute, "logger", mockLogger)

      when(mockLogger.isEnabled(Level.DEBUG)).thenReturn(false)
      when(mockLogger.isEnabled(Level.TRACE)).thenReturn(false)
      when(mockLogger.isEnabled(Level.INFO)).thenReturn(false)
      when(mockLogger.isEnabled(Level.WARN)).thenReturn(false)
      when(mockLogger.isEnabled(Level.ERROR)).thenReturn(true)

      jbossLoggingLogChute.isLevelEnabled(LogChute.ERROR_ID) should be (true)
    }

    it("log message") {
      val mockLogger = mock[Logger]

      val jbossLoggingLogChute = new JBossLoggingLogChute
      Whitebox.setInternalState(jbossLoggingLogChute, "logger", mockLogger)

      jbossLoggingLogChute.log(LogChute.DEBUG_ID, "debug message")
      jbossLoggingLogChute.log(LogChute.TRACE_ID, "trace message")
      jbossLoggingLogChute.log(LogChute.INFO_ID, "info message")
      jbossLoggingLogChute.log(LogChute.WARN_ID, "warn message")
      jbossLoggingLogChute.log(LogChute.ERROR_ID, "error message")

      verify(mockLogger).debug("debug message")
      verify(mockLogger).trace("trace message")
      verify(mockLogger).info("info message")
      verify(mockLogger).warn("warn message")
      verify(mockLogger).error("error message")
    }

    it("log message with Throwable") {
      val mockLogger = mock[Logger]

      val jbossLoggingLogChute = new JBossLoggingLogChute
      Whitebox.setInternalState(jbossLoggingLogChute, "logger", mockLogger)

      val th = new Throwable

      jbossLoggingLogChute.log(LogChute.DEBUG_ID, "debug message", th)
      jbossLoggingLogChute.log(LogChute.TRACE_ID, "trace message", th)
      jbossLoggingLogChute.log(LogChute.INFO_ID, "info message", th)
      jbossLoggingLogChute.log(LogChute.WARN_ID, "warn message", th)
      jbossLoggingLogChute.log(LogChute.ERROR_ID, "error message", th)

      verify(mockLogger).debug("debug message", th)
      verify(mockLogger).trace("trace message", th)
      verify(mockLogger).info("info message", th)
      verify(mockLogger).warn("warn message", th)
      verify(mockLogger).error("error message", th)
    }
  }
}
