package de.gedoplan.buch.eedemos.cdi.sub1.interceptor;

import javax.annotation.Priority;
import javax.interceptor.Interceptor;

/**
 * Interceptor-Implementierung zu {@link Two}.
 * 
 * @author dw
 */
@Two
@Interceptor
@Priority(Interceptor.Priority.APPLICATION + 2)
public class TwoInterceptor extends OneTwoThreeInterceptor
{
  private static final long serialVersionUID = 1L;
}
