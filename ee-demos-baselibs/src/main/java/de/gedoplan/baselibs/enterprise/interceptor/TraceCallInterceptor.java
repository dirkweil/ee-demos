package de.gedoplan.baselibs.enterprise.interceptor;

// CHECKSTYLE:OFF

import java.io.Serializable;
import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.util.Locale;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Interceptor-Implementierung zu {@link TraceCall}.
 * 
 * @author dw
 */
@TraceCall
@Interceptor
public class TraceCallInterceptor implements Serializable
{
  private Log                       logger;

  private static final NumberFormat NANO_FORMATTER = NumberFormat.getIntegerInstance(Locale.GERMAN);

  /**
   * Interceptor-Arbeitsmethode.
   * 
   * @param invocationContext InvocationContext
   * @return Returnwert
   * @throws Exception bei Fehlern
   */
  @AroundInvoke
  public Object traceCall(InvocationContext invocationContext) throws Exception
  {
    if (this.logger == null)
    {
      Class<? extends Object> targetClass = invocationContext.getTarget().getClass();
      this.logger = LogFactory.getLog(targetClass);
    }

    Method method = null;
    long startTime = 0;
    Object resultMsg = null;

    /*
     * Achtung: Auf JBoss 7.0.2 kommen TRACE-Meldungen nicht ins Log. (Ursache: ACL (Apache Commons Logging) nutzt auf Jboss 7 JUL
     * (Java Util Logging), das wiederum auf JBoss Logging umgesetzt wird. ALC TRACE wird als JUL FINEST intepretiert, was JBoss 7
     * trotz TRACE-Einstellung ignoriert.)
     */
    boolean traceEnabled = this.logger.isTraceEnabled();

    try
    {
      if (traceEnabled)
      {
        method = invocationContext.getMethod();

        StringBuilder buf = new StringBuilder(method.getName());
        buf.append('(');
        String sep = "";
        Object[] parameters = invocationContext.getParameters();
        if (parameters != null)
        {
          for (Object parm : parameters)
          {
            buf.append(sep + toLoggingString(parm));
            sep = ",";
          }
        }
        buf.append(')');
        this.logger.trace(buf.toString());

        startTime = System.nanoTime();
      }
    }
    catch (Exception ignore) // CHECKSTYLE:IGNORE Ignorieren von Exceptions ist hier OK
    {
    }

    try
    {
      Object result = invocationContext.proceed();

      if (traceEnabled)
      {
        if (method.getReturnType() == void.class)
        {
          resultMsg = "return";
        }
        else
        {
          resultMsg = "return " + toLoggingString(result);
        }
      }

      return result;
    }
    catch (Exception e) // CHECKSTYLE:IGNORE Fangen von Exceptions ist hier OK
    {
      try
      {
        if (traceEnabled)
        {
          resultMsg = "throw " + e.toString();
        }
      }
      catch (Exception ignore) // CHECKSTYLE:IGNORE Ignorieren von Exceptions ist hier OK
      {
      }

      throw e;
    }
    finally
    {
      try
      {
        if (traceEnabled)
        {
          long usedNanos = System.nanoTime() - startTime;
          this.logger.trace(method.getName() + ": " + resultMsg + " (" + NANO_FORMATTER.format(usedNanos) + " ns)");
        }
      }
      catch (Exception ignore) // CHECKSTYLE:IGNORE Ignorieren von Exceptions ist hier OK
      {
      }
    }
  }

  private String toLoggingString(Object obj)
  {
    if (obj == null)
    {
      return "null";
    }

    if (obj instanceof Iterable<?>)
    {
      StringBuilder buf = new StringBuilder("[");
      String sep = "";
      for (Object subObj : (Iterable<?>) obj)
      {
        buf.append(sep);
        sep = ",";
        buf.append(toLoggingString(subObj));
      }
      buf.append("]");
      return buf.toString();
    }

    return obj.toString();
  }
}
