package de.gedoplan.buch.eedemos.jsf.listener;

import javax.faces.component.UIComponent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DemoValueChangeListener implements ValueChangeListener
{
  // Injection per @Inject hier leider nicht möglich
  private Log log = LogFactory.getLog(DemoValueChangeListener.class);

  @Override
  public void processValueChange(ValueChangeEvent event)
  {
    UIComponent source = event.getComponent();
    String sourceType = source.getClass().getSimpleName();
    String clientId = "@" + source.getClientId();
    this.log.debug("ValueChangeEvent von " + sourceType + clientId);
    this.log.debug("  " + event.getOldValue() + " -> " + event.getNewValue());
  }

}
