package de.gedoplan.buch.eedemos.jsf.listener;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DemoActionListener implements ActionListener
{
  // Injection per @Inject hier leider nicht möglich
  private Log log = LogFactory.getLog(DemoActionListener.class);

  @Override
  public void processAction(ActionEvent event)
  {
    UIComponent source = event.getComponent();
    String sourceType = source.getClass().getSimpleName();
    String clientId = "@" + source.getClientId();
    this.log.debug("ActionEvent von " + sourceType + clientId);
  }
}
