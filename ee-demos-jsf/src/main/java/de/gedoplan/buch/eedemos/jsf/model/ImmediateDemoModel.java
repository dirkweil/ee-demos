package de.gedoplan.buch.eedemos.jsf.model;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.PhaseId;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.logging.Log;

@Named
@RequestScoped
public class ImmediateDemoModel
{
  @Inject
  private Log    log;

  private String text1 = "text1";
  private String text2 = "text2";

  public String getText1()
  {
    return this.text1;
  }

  public void validateText1(FacesContext facesContext, UIComponent uiComponent, java.lang.Object object)
  {
    this.log.debug("text1: " + PhaseId.PROCESS_VALIDATIONS);
  }

  public void setText1(String text1)
  {
    this.log.debug("text1: " + PhaseId.UPDATE_MODEL_VALUES);
    this.text1 = text1;
  }

  public void renderText1(ComponentSystemEvent event)
  {
    this.log.debug("text1: " + PhaseId.RENDER_RESPONSE);
  }

  public String gettext2()
  {
    return this.text2;
  }

  public void validatetext2(FacesContext facesContext, UIComponent uiComponent, java.lang.Object object)
  {
    this.log.debug("text2: " + PhaseId.PROCESS_VALIDATIONS);
  }

  public void settext2(String text2)
  {
    this.log.debug("text2: " + PhaseId.UPDATE_MODEL_VALUES);
    this.text2 = text2;
  }

  public void rendertext2(ComponentSystemEvent event)
  {
    this.log.debug("text2: " + PhaseId.RENDER_RESPONSE);
  }

  public void ok()
  {
    this.log.debug("ok   : " + PhaseId.INVOKE_APPLICATION);
  }

  public void cancel()
  {
    this.log.debug("cncl : " + PhaseId.INVOKE_APPLICATION);
  }

  public String getDummy()
  {
    this.log.debug("-----");
    return "";
  }
}
