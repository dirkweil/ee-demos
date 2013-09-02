package de.gedoplan.buch.eedemos.jsf.listener;

import javax.faces.component.UIViewRoot;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DemoPhaseListener implements PhaseListener
{
  private static final long serialVersionUID = 1L;

  // Injection per @Inject hier leider nicht möglich
  private Log               log              = LogFactory.getLog(DemoPhaseListener.class);

  @Override
  public PhaseId getPhaseId()
  {
    return PhaseId.ANY_PHASE;
  }

  @Override
  public void beforePhase(PhaseEvent event)
  {
    logEvent("beforePhase", event);
  }

  @Override
  public void afterPhase(PhaseEvent event)
  {
    logEvent("afterPhase", event);
  }

  private void logEvent(String when, PhaseEvent event)
  {
    UIViewRoot viewRoot = event.getFacesContext().getViewRoot();
    String viewId = viewRoot != null ? viewRoot.getViewId() : "???";
    this.log.debug(when + " " + event.getPhaseId() + " of " + viewId);
  }
}
