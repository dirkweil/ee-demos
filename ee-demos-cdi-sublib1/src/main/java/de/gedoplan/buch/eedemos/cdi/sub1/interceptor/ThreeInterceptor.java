package de.gedoplan.buch.eedemos.cdi.sub1.interceptor;

import javax.interceptor.Interceptor;

/**
 * Interceptor-Implementierung zu {@link Three}.
 * 
 * @author dw
 */
@Three
@Interceptor
public class ThreeInterceptor extends OneTwoThreeInterceptor
{
  private static final long serialVersionUID = 1L;
}
