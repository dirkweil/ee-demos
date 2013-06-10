package de.gedoplan.buch.eedemos.jsf.model;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Named
@RequestScoped
public class GetSupportModel implements Serializable
{
  private Log    log = LogFactory.getLog(GetSupportModel.class);

  private String name;

  public String getName()
  {
    return this.name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public void logPreRenderEvent(ComponentSystemEvent event)
  {
    String eventType = event.getClass().getSimpleName();
    UIComponent source = event.getComponent();
    String sourceType = source.getClass().getSimpleName();
    this.log.debug(eventType + " von " + sourceType + "@" + source.getClientId() + ", name=" + this.name);
  }
}
