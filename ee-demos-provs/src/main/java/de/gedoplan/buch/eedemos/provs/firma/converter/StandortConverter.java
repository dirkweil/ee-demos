package de.gedoplan.buch.eedemos.provs.firma.converter;

import de.gedoplan.buch.eedemos.provs.firma.entity.Standort;
import de.gedoplan.buch.eedemos.provs.firma.repository.StandortRepository;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

/**
 * Converter für {@link Standort}.
 * 
 * @author dw
 */
@FacesConverter(forClass = Standort.class)
@RequestScoped
public class StandortConverter implements Converter
{
  @Inject
  private StandortRepository standortRepository;

  @Override
  public Object getAsObject(FacesContext context, UIComponent component, String value)
  {
    if (value == null)
    {
      return "";
    }

    try
    {
      final Integer id = Integer.valueOf(value);
      return this.standortRepository.findById(id);
    }
    catch (NumberFormatException e)
    {
      // ignore
    }

    FacesMessage msg = new FacesMessage("Unbekannter Standort: " + value);
    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
    throw new ConverterException(msg);
  }

  @Override
  public String getAsString(FacesContext context, UIComponent component, Object value)
  {
    if (value == null || "".equals(value))
    {
      return null;
    }

    Standort standort = (Standort) value;
    Integer id = standort.getId();
    return id != null ? id.toString() : null;
  }

}
