package org.velocity4s.log

import org.apache.velocity.runtime.RuntimeServices
import org.apache.velocity.runtime.log.LogChute

import org.jboss.logging.Logger
import org.jboss.logging.Logger.Level

object JBossLoggingLogChute {
  val LOGCHUTE_JBOSS_LOGGING_NAME: String = "velocity4s.log.logsystem.jbosslogging.name"
  val DEFAULT_LOG_NAME: String = "org.apache.velocity"
}

class JBossLoggingLogChute extends LogChute {
  protected var logger: Logger = _

  override def init(rs: RuntimeServices): Unit = {
    val name =
      Option(rs.getProperty(JBossLoggingLogChute.LOGCHUTE_JBOSS_LOGGING_NAME))
        .getOrElse(JBossLoggingLogChute.DEFAULT_LOG_NAME)

    logger = Logger.getLogger(name.toString)
    log(LogChute.DEBUG_ID, s"${getClass.getSimpleName} name is '$name'")
  }

  override def isLevelEnabled(level: Int): Boolean =
    level match {
      case LogChute.DEBUG_ID => logger.isEnabled(Level.DEBUG)
      case LogChute.INFO_ID  => logger.isEnabled(Level.INFO)
      case LogChute.TRACE_ID => logger.isEnabled(Level.TRACE)
      case LogChute.WARN_ID  => logger.isEnabled(Level.WARN)
      case LogChute.ERROR_ID => logger.isEnabled(Level.ERROR)
      case _                 => true
    }

  override def log(level: Int, message: String): Unit =
    level match {
      case LogChute.TRACE_ID     => logger.trace(message)
      case LogChute.INFO_ID      => logger.info(message)
      case LogChute.WARN_ID      => logger.warn(message)
      case LogChute.ERROR_ID     => logger.error(message)
      case LogChute.DEBUG_ID | _ => logger.debug(message)
    }

  override def log(level: Int, message: String, t: Throwable): Unit =
    level match {
      case LogChute.TRACE_ID     => logger.trace(message, t)
      case LogChute.INFO_ID      => logger.info(message, t)
      case LogChute.WARN_ID      => logger.warn(message, t)
      case LogChute.ERROR_ID     => logger.error(message, t)
      case LogChute.DEBUG_ID | _ => logger.debug(message, t)
    }
}
