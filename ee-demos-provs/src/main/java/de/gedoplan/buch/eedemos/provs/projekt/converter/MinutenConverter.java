package de.gedoplan.buch.eedemos.provs.projekt.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 * Converter für Minuten im Format #0:00.
 * 
 * @author dw
 */
@FacesConverter("de.gedoplan.provs.MinutenConverter")
public class MinutenConverter implements Converter
{
  @Override
  public Object getAsObject(FacesContext context, UIComponent component, String value)
  {

    if (value == null)
    {
      return 0;
    }

    value = value.trim();
    if (value.isEmpty())
    {
      return 0;
    }

    try
    {
      String[] teilString = value.split("\\s*:\\s*");
      if (teilString.length <= 2)
      {
        int minuten = 0;
        for (int i = 0; i < teilString.length; ++i)
        {
          int teilWert = Integer.parseInt(teilString[i]);
          minuten = 60 * minuten + teilWert;
        }
        return minuten;
      }
    }
    catch (Exception e) // CHECKSTYLE:IGNORE
    {
      // ignore
    }

    FacesMessage msg = new FacesMessage("Ungültiges Zeitformat: " + value);
    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
    throw new ConverterException(msg);
  }

  @Override
  public String getAsString(FacesContext context, UIComponent component, Object value)
  {
    String string = null;

    if (value instanceof Integer)
    {
      int minuten = (Integer) value;
      string = minuten == 0 ? "" : String.format("%2d:%02d", minuten / 60, minuten % 60);
    }

    return string;
  }
}
