package de.gedoplan.buch.eedemos.provs.firma.converter;

import de.gedoplan.buch.eedemos.provs.firma.entity.Mitarbeiter;
import de.gedoplan.buch.eedemos.provs.firma.repository.MitarbeiterRepository;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

/**
 * Converter für {@link Mitarbeiter}.
 * 
 * @author dw
 */
@FacesConverter(forClass = Mitarbeiter.class)
@RequestScoped
public class MitarbeiterConverter implements Converter
{
  @Inject
  private MitarbeiterRepository mitarbeiterRepository;

  @Override
  public Object getAsObject(FacesContext context, UIComponent component, String value)
  {
    if (value == null)
    {
      return "";
    }

    try
    {
      final Integer mitarbeiterId = Integer.valueOf(value);
      return this.mitarbeiterRepository.findById(mitarbeiterId);
    }
    catch (NumberFormatException e)
    {
      // ignore
    }

    List<Mitarbeiter> mitarbeiterListe = this.mitarbeiterRepository.findByToStringPrefix(value);
    if (mitarbeiterListe.size() == 1)
    {
      return mitarbeiterListe.get(0);
    }

    FacesMessage msg = new FacesMessage("Unbekannter Mitarbeiter: " + value);
    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
    throw new ConverterException(msg);
  }

  @Override
  public String getAsString(FacesContext context, UIComponent component, Object value)
  {
    if ("".equals(value))
    {
      return null;
    }

    Mitarbeiter mitarbeiter = (Mitarbeiter) value;
    return mitarbeiter.getId().toString();
  }

}
