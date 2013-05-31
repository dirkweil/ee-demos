package de.gedoplan.buch.eedemos.cdi.sub.interceptor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Interceptor-Basis-Implementierung zu {@link One}, {@link Two}, {@link Three}.
 * 
 * @author dw
 */
public abstract class OneTwoThreeInterceptor implements Serializable
{
  protected Log logger = LogFactory.getLog(this.getClass());

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
    this.logger.debug("Intercepting " + invocationContext.getMethod());
    history.add(new HistoryEntry(this.getClass(), invocationContext.getMethod().toGenericString()));
    return invocationContext.proceed();
  }

  public static class HistoryEntry
  {
    private Class<?> interceptorClass;
    private String   interceptedMethod;

    public HistoryEntry(Class<?> interceptorClass, String interceptedMethod)
    {
      this.interceptorClass = interceptorClass;
      this.interceptedMethod = interceptedMethod;
    }

    public Class<?> getInterceptorClass()
    {
      return this.interceptorClass;
    }

    public String getInterceptedMethod()
    {
      return this.interceptedMethod;
    }

    @Override
    public String toString()
    {
      return "HistoryEntry [interceptorClass=" + this.interceptorClass + ", interceptedMethod=" + this.interceptedMethod + "]";
    }
  }

  private static List<HistoryEntry> history = new ArrayList<>();

  public static List<HistoryEntry> getHistory()
  {
    return Collections.unmodifiableList(history);
  }

  public static void clearHistory()
  {
    history.clear();
  }
}
