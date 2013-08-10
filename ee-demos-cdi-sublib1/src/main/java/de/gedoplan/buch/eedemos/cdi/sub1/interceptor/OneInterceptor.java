package de.gedoplan.buch.eedemos.cdi.sub1.interceptor;

import javax.annotation.Priority;
import javax.interceptor.Interceptor;

/**
 * Interceptor-Implementierung zu {@link One}.
 *
 * @author dw
 */
@One
@Interceptor
@Priority(Interceptor.Priority.APPLICATION + 1)
public class OneInterceptor extends OneTwoThreeInterceptor
{
}
