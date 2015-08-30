package de.gedoplan.buch.eedemos.cdi.observer;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.EventMetadata;

import org.apache.commons.logging.Log;

public class EventLogger
{
  public static void logEvent(@Observes Object event, EventMetadata eventMetadata, Log log)
  {
    if (log.isTraceEnabled())
    {
      String metaData = "";
      if (eventMetadata != null)
      {
        metaData = ", qualifiers: " + eventMetadata.getQualifiers() + ", injectionPoint: " + eventMetadata.getInjectionPoint();
      }
      else
      {
        metaData = " (no event meta data!)";
      }

      log.trace("Event: " + event + metaData);
    }
  }

}
