package de.gedoplan.baselibs.enterprise.interceptor;

import java.io.Serializable;
import java.rmi.RemoteException;

import javax.annotation.Resource;
import javax.ejb.ApplicationException;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.apache.commons.logging.Log;

/**
 * Interceptor-Implementierung zu {@link TransactionRequired}.
 * 
 * @author dw
 */
@TransactionRequired
@Interceptor
public class TransactionRequiredInterceptor implements Serializable
{
  @Inject
  Log             logger;

  @Resource
  UserTransaction userTransaction;

  /**
   * Interceptor-Arbeitsmethode.
   * 
   * @param invocationContext InvocationContext
   * @return Returnwert
   * @throws Exception bei Fehlern
   */
  @AroundInvoke
  public Object manageTransaction(InvocationContext invocationContext) throws Exception
  {
    // Falls schon eine TX aktiv, Methode direkt aufrufen
    if (isTransactionActive())
    {
      return invocationContext.proceed();
    }

    // TX beginnen
    if (this.logger.isDebugEnabled())
    {
      this.logger.debug("Begin tx");
    }
    this.userTransaction.begin();

    // Methode aufrufen
    Object result = null;
    Exception exception = null;
    try
    {
      result = invocationContext.proceed();
    }
    catch (Exception e) // CHECKSTYLE:IGNORE Allgemeine Exception ist hier OK
    {
      exception = e;
    }

    boolean rollback = exception != null && getRollbackDesignation(exception);

    if (!rollback)
    {
      // TX committen
      if (this.logger.isDebugEnabled())
      {
        this.logger.debug("Commit tx");
      }
      this.userTransaction.commit();
    }
    else
    {
      // TX zurückrollen
      if (this.logger.isDebugEnabled())
      {
        this.logger.debug("Rollback tx");
      }
      try
      {
        this.userTransaction.rollback();
      }
      catch (Throwable ignore)
      {
      }
    }

    if (exception != null)
    {
      throw exception;
    }

    return result;
  }

  // Ist die TX aktiv?
  private boolean isTransactionActive() throws SystemException
  {
    return this.userTransaction.getStatus() == Status.STATUS_ACTIVE;
  }

  // TX-Rollback aufgrund der angegebenen Exception?
  private boolean getRollbackDesignation(Exception exception)
  {
    // Falls die Exception mit ApplicationException annotiert ist, bestimmt deren Parameter, ob Rollback
    ApplicationException applicationException = extractApplicationExceptionAnnotation(exception.getClass());
    if (applicationException != null)
    {
      return applicationException.rollback();
    }
    else
    {
      // sonst: System Exception --> Rollback
      return exception instanceof RuntimeException || exception instanceof RemoteException;
    }
  }

  // ApplicationException-Annotation besorgen. Kann an der Klasse oder einer Basisklasse (dort aber mit inherited=true) stehen.
  private ApplicationException extractApplicationExceptionAnnotation(Class<?> clazz)
  {
    ApplicationException applicationException = clazz.getAnnotation(ApplicationException.class);
    if (applicationException != null)
    {
      return applicationException;
    }

    Class<?> superClass = clazz.getSuperclass();
    if (superClass == null)
    {
      return null;
    }

    applicationException = extractApplicationExceptionAnnotation(superClass);
    if (applicationException != null && applicationException.inherited())
    {
      return applicationException;
    }

    return null;
  }
}