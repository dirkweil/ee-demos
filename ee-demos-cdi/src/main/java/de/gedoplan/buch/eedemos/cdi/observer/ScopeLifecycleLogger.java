package de.gedoplan.buch.eedemos.cdi.observer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;

import org.apache.commons.logging.Log;

public class ScopeLifecycleLogger
{
  public static void logRequestContextInitialized(@Observes @Initialized(RequestScoped.class) Object event, Log log)
  {
    logScopeLifecycleEvent(true, "request", log);
  }

  public static void logRequestContextDestroyed(@Observes @Destroyed(RequestScoped.class) Object event, Log log)
  {
    logScopeLifecycleEvent(false, "request", log);
  }

  public static void logSessionContextInitialized(@Observes @Initialized(SessionScoped.class) Object event, Log log)
  {
    logScopeLifecycleEvent(true, "session", log);
  }

  public static void logSessionContextDestroyed(@Observes @Destroyed(SessionScoped.class) Object event, Log log)
  {
    logScopeLifecycleEvent(false, "session", log);
  }

  public static void logApplicationContextInitialized(@Observes @Initialized(ApplicationScoped.class) Object event, Log log)
  {
    logScopeLifecycleEvent(true, "application", log);
  }

  public static void logApplicationContextDestroyed(@Observes @Destroyed(ApplicationScoped.class) Object event, Log log)
  {
    logScopeLifecycleEvent(false, "application", log);
  }

  private static void logScopeLifecycleEvent(boolean init, String scopeName, Log log)
  {
    if (log.isTraceEnabled())
    {
      log.trace((init ? "Initialized" : "Destroyed") + " " + scopeName + " scope");
    }
  }
}
