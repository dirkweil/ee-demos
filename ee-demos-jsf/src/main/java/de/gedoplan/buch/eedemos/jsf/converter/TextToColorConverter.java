package de.gedoplan.buch.eedemos.jsf.converter;

import java.awt.Color;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = Color.class)
public class TextToColorConverter implements Converter
{
  @Override
  public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String uiValue)
  {
    if ("red".equalsIgnoreCase(uiValue))
    {
      return Color.red;
    }
    if ("blue".equalsIgnoreCase(uiValue))
    {
      return Color.blue;
    }
    if ("green".equalsIgnoreCase(uiValue))
    {
      return Color.green;
    }

    FacesMessage msg = new FacesMessage("Ungültiger Farbname; erlaubt sind red, blue, green.");
    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
    throw new ConverterException(msg);
  }

  @Override
  public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object beanValue)
  {
    if (beanValue instanceof Color)
    {
      if (Color.red.equals(beanValue))
      {
        return "red";
      }
      if (Color.blue.equals(beanValue))
      {
        return "blue";
      }
      if (Color.green.equals(beanValue))
      {
        return "green";
      }
    }
    return null;
  }

}
