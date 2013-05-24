package de.gedoplan.buch.eedemos.cdi.sub.interceptor;

import java.io.Serializable;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.apache.commons.logging.Log;

/**
 * Interceptor-Implementierung zu {@link Two}.
 * 
 * @author dw
 */
@Two
@Interceptor
@Priority(2)
public class TwoInterceptor implements Serializable
{
  @Inject
  private Log logger;

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
    return invocationContext.proceed();
  }
}
