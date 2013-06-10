package de.gedoplan.buch.eedemos.jsf.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("de.gedoplan.jsf.TextToIntConverter")
public class TextToIntConverter implements Converter
{
  @Override
  public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String uiValue)
  {
    if ("null".equalsIgnoreCase(uiValue))
    {
      return 0;
    }
    if ("eins".equalsIgnoreCase(uiValue))
    {
      return 1;
    }
    if ("zwei".equalsIgnoreCase(uiValue))
    {
      return 2;
    }
    if ("drei".equalsIgnoreCase(uiValue))
    {
      return 3;
    }

    FacesMessage msg = new FacesMessage("Kann nur bis drei zählen.");
    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
    throw new ConverterException(msg);
  }

  @Override
  public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object beanValue)
  {
    if (beanValue instanceof Number)
    {
      switch (((Number) beanValue).intValue())
      {
      case 0:
        return "null";
      case 1:
        return "eins";
      case 2:
        return "zwei";
      case 3:
        return "drei";
      }
    }
    return null;
  }

}
