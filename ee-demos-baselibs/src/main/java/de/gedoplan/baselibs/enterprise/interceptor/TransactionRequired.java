package de.gedoplan.baselibs.enterprise.interceptor;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.ejb.TransactionAttributeType;
import javax.interceptor.InterceptorBinding;

/**
 * Interceptor-Binding zur Transaktionssteuerung.
 * 
 * Durch Annotation einer CDI-Bean auf Klassen- oder Methodenebene hiermit wird erreicht, dass die betroffenen Methoden
 * transaktional ablaufen. Des Verfahrens entspricht dem EJB-Transaktionsmodus {@link TransactionAttributeType#REQUIRED}.
 * 
 * @author dw
 */
@InterceptorBinding
@Target({ TYPE, METHOD })
@Retention(RUNTIME)
public @interface TransactionRequired
{
}