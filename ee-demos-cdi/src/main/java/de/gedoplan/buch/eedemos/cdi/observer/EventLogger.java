package de.gedoplan.buch.eedemos.cdi.observer;

import javax.enterprise.event.Observes;

import org.apache.commons.logging.Log;

public class EventLogger
{
  public static void logEvent(@Observes Object event, Log logger)
  {
    if (logger.isDebugEnabled())
    {
      logger.debug("Event: " + event);
    }
  }

}
