package org.velocity4s.log

import org.apache.velocity.runtime.RuntimeServices
import org.apache.velocity.runtime.log.LogChute

import org.slf4j.{ Logger, LoggerFactory }

object Slf4jLogChute {
  val LOGCHUTE_SLF4J_NAME: String = "velocity4s.log.logsystem.slf4j.name"
  val DEFAULT_LOG_NAME: String = "org.apache.velocity"
}

class Slf4jLogChute extends LogChute {
  protected var logger: Logger = _

  override def init(rs: RuntimeServices): Unit = {
    val name =
      Option(rs.getProperty(Slf4jLogChute.LOGCHUTE_SLF4J_NAME.asInstanceOf[String]))
        .getOrElse(Slf4jLogChute.DEFAULT_LOG_NAME)

    logger = LoggerFactory.getLogger(name.toString)
    log(LogChute.DEBUG_ID, s"${getClass.getSimpleName} name is '$name'")
  }

  override def isLevelEnabled(level: Int): Boolean =
    level match {
      case LogChute.DEBUG_ID => logger.isDebugEnabled
      case LogChute.TRACE_ID => logger.isTraceEnabled
      case LogChute.INFO_ID  => logger.isInfoEnabled
      case LogChute.WARN_ID  => logger.isWarnEnabled
      case LogChute.ERROR_ID => logger.isErrorEnabled
      case _                 => true
    }

  override def log(level: Int, message: String): Unit =
    level match {
      case LogChute.INFO_ID      => logger.info(message)
      case LogChute.TRACE_ID     => logger.trace(message)
      case LogChute.WARN_ID      => logger.warn(message)
      case LogChute.ERROR_ID     => logger.error(message)
      case LogChute.DEBUG_ID | _ => logger.debug(message)
    }

  override def log(level: Int, message: String, t: Throwable): Unit =
    level match {
      case LogChute.INFO_ID      => logger.info(message, t)
      case LogChute.TRACE_ID     => logger.trace(message, t)
      case LogChute.WARN_ID      => logger.warn(message, t)
      case LogChute.ERROR_ID     => logger.error(message, t)
      case LogChute.DEBUG_ID | _ => logger.debug(message, t)
    }
}
