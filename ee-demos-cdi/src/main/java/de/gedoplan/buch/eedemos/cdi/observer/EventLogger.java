package de.gedoplan.buch.eedemos.cdi.observer;

import javax.enterprise.event.Observes;

import org.apache.commons.logging.Log;

public class EventLogger
{
  public static void logEvent(@Observes Object event, Log log)
  {
    if (log.isTraceEnabled())
    {
      log.trace("Event: " + event);
    }
  }

  // TODO: Injektion von EventMetaData in GLF-4.0-b86 schlägt fehl
  //  public static void logEvent(@Observes Object event, EventMetadata eventMetadata, Log log)
  //  {
  //    if (log.isTraceEnabled())
  //    {
  //      log.trace("Event: " + eventMetadata.getQualifiers() + " " + event);
  //    }
  //  }

}
