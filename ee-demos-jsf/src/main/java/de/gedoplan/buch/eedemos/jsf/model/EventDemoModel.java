package de.gedoplan.buch.eedemos.jsf.model;

import java.io.Serializable;
import java.util.EventObject;

import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.logging.Log;

@SessionScoped
@Named
public class EventDemoModel implements Serializable
{
  @Inject
  private Log    log;

  private String someText = "";

  public String getSomeText()
  {
    return this.someText;
  }

  public void setSomeText(String someText)
  {
    this.someText = someText;
  }

  public void doOk()
  {
    this.log.debug("doOk");
  }

  public void handleAction(ActionEvent event)
  {
    logEvent(event);
  }

  public void handleValueChange(ValueChangeEvent event)
  {
    logEvent(event);
    this.log.debug("  " + event.getOldValue() + " -> " + event.getNewValue());
  }

  public void handleComponentSystemEvent(ComponentSystemEvent event)
  {
    logEvent(event);
  }

  private void logEvent(EventObject event)
  {
    String eventType = event.getClass().getSimpleName();
    Object source = event.getSource();
    String sourceType = source.getClass().getSimpleName();
    String clientId = "";
    if (source instanceof UIComponent)
    {
      clientId = "@" + ((UIComponent) source).getClientId();
    }
    this.log.debug(eventType + " von " + sourceType + clientId);
  }
}
