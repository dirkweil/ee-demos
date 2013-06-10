package de.gedoplan.baselibs.utils.logging;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Logger-Producer.
 * 
 * @author dw
 */
public class LoggerProducer
{
  /**
   * Logger liefern.
   * 
   * @param injectionPoint Injection-Point
   * @return Logger
   */
  @Produces
  public static Log getLogger(InjectionPoint injectionPoint)
  {
    Class<?> targetClass = injectionPoint.getMember().getDeclaringClass();
    return LogFactory.getLog(targetClass);
  }

  private LoggerProducer()
  {
  }
}
