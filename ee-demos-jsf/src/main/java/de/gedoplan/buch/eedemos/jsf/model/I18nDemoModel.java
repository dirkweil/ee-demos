package de.gedoplan.buch.eedemos.jsf.model;

import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@RequestScoped
public class I18nDemoModel
{
  public Date getCurrentDate()
  {
    return new Date();
  }

  public double getSomeDecimalValue()
  {
    return 1234.56;
  }

  public Locale getLocale()
  {
    Locale locale = new Locale("de", "DE");
    return locale;
  }

  public int getMaxSize()
  {
    return 50;
  }

  public int getCurSize()
  {
    return 54;
  }

  public String getMessageFromBundle(String bundleVarName, String messageKey)
  {
    try
    {
      FacesContext facesContext = FacesContext.getCurrentInstance();
      ResourceBundle bundle = facesContext.getApplication().getResourceBundle(facesContext, bundleVarName);
      return bundle.getString(messageKey);
    }
    catch (Exception e) // CHECKSTYLE:IGNORE
    {
      return "???" + messageKey + "???";
    }
  }
}
