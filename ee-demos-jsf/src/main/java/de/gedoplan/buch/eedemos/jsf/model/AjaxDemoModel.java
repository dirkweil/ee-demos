package de.gedoplan.buch.eedemos.jsf.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;

@SessionScoped
@Named
public class AjaxDemoModel implements Serializable
{
  private static final long             serialVersionUID = 1L;

  private static final SimpleDateFormat FORMATTER        = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");

  private int                           counter;

  private StringBuffer                  message          = new StringBuffer();

  public String getCurrentDate()
  {
    return FORMATTER.format(new Date());
  }

  public int getCounter()
  {
    return this.counter;
  }

  /*
   * Achtung: Dies ist kein "richtiger" Setter: Er setzt den internen Wert um 1 höher als den Parameter. Dadurch kann in der View
   * beobachtet werden, ob ein Aufruf erfolgte.
   */
  public void setCounter(int counter)
  {
    this.counter = counter + 1;
  }

  public String getMessage()
  {
    return this.message.toString();
  }

  public void logActionEvent(ActionEvent actionEvent)
  {
    this.message.append("ActionEvent von ").append(actionEvent.getComponent().getId()).append('\n');
  }

  public void logValueChangeEvent(ValueChangeEvent valueChangeEvent)
  {
    this.message.append("ValueChangeEvent von ").append(valueChangeEvent.getComponent().getId()).append('\n');
  }

  public void logAjaxBehaviorEvent(AjaxBehaviorEvent ajaxBehaviorEvent)
  {
    this.message.append("AjaxBehaviorEvent von ").append(ajaxBehaviorEvent.getComponent().getId()).append('\n');
  }

}
