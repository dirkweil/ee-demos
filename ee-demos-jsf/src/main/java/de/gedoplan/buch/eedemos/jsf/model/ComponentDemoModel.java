package de.gedoplan.buch.eedemos.jsf.model;

import java.io.Serializable;
import java.util.Date;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.logging.Log;

@Named
@SessionScoped
public class ComponentDemoModel implements Serializable
{
  private static final long serialVersionUID = 1L;

  @Inject
  private Log               log;

  private int               someInt          = 42;
  private double            someDouble       = 42;
  private Date              someDate         = new Date();

  public int getSomeInt()
  {
    return this.someInt;
  }

  public void setSomeInt(int someInt)
  {
    this.someInt = someInt;
  }

  public double getSomeDouble()
  {
    return this.someDouble;
  }

  public void setSomeDouble(double someDouble)
  {
    this.someDouble = someDouble;
    this.log.debug("someDouble: " + someDouble);
  }

  public Date getSomeDate()
  {
    return this.someDate;
  }

  public void setSomeDate(Date someDate)
  {
    this.log.debug("setSomeDate: " + someDate);
    this.someDate = someDate;
  }

  public String doOk()
  {
    this.log.debug("ok");
    return null;
  }

  public String doCancel()
  {
    this.log.debug("cancel");
    return null;
  }

  public void logAction()
  {
    this.log.debug("Action");
  }
}
