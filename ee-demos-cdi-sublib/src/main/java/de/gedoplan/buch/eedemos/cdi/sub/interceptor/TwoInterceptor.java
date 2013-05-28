package de.gedoplan.buch.eedemos.cdi.sub.interceptor;

import javax.annotation.Priority;
import javax.interceptor.Interceptor;

/**
 * Interceptor-Implementierung zu {@link Two}.
 * 
 * @author dw
 */
@Two
@Interceptor
@Priority(2)
public class TwoInterceptor extends OneTwoThreeInterceptor
{
}
