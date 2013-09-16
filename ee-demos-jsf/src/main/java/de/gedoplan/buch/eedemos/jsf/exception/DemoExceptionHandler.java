package de.gedoplan.buch.eedemos.jsf.exception;

import java.util.Iterator;

import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

public class DemoExceptionHandler extends ExceptionHandlerWrapper
{
  private ExceptionHandler wrapped;

  public DemoExceptionHandler(ExceptionHandler wrapped)
  {
    this.wrapped = wrapped;
  }

  @Override
  public ExceptionHandler getWrapped()
  {
    return this.wrapped;
  }

  @Override
  public void handle()
  {
    FacesContext facesContext = FacesContext.getCurrentInstance();
    NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();

    // Bislang unbehandelte Exception(event)s durchiterieren
    Iterator<ExceptionQueuedEvent> eventIterator = getUnhandledExceptionQueuedEvents().iterator();
    while (eventIterator.hasNext())
    {
      // Exception ermitteln
      ExceptionQueuedEvent event = eventIterator.next();
      ExceptionQueuedEventContext eventContext = (ExceptionQueuedEventContext) event.getSource();
      Throwable exception = eventContext.getException();

      try
      {
        // Message erzeugen
        FacesMessage message = new FacesMessage("Fehler in " + facesContext.getViewRoot().getViewId() + ": " + exception);
        message.setSeverity(FacesMessage.SEVERITY_ERROR);
        facesContext.addMessage(null, message);

        // Zu bestimmter View navigieren
        navigationHandler.handleNavigation(facesContext, null, "/index");

        // Requestbearbeitung nach aktueller Phase beenden
        facesContext.renderResponse();

        // Behandelten Event entfernen
        eventIterator.remove();
      }
      catch (Exception ignore) // CHECKSTYLE:IGNORE
      {
        // ignore
      }
    }

    // Ggf. noch unbehandelte Events von nächstem Handler bearbeiten lassen
    getWrapped().handle();
  }
}
