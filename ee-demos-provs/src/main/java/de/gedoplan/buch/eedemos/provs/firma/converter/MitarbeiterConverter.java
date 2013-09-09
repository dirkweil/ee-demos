package de.gedoplan.buch.eedemos.provs.firma.converter;

import de.gedoplan.buch.eedemos.provs.firma.entity.Mitarbeiter;
import de.gedoplan.buch.eedemos.provs.firma.repository.MitarbeiterRepository;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.deltaspike.core.api.provider.BeanProvider;

/**
 * Converter für {@link Mitarbeiter}.
 * 
 * @author dw
 */
@FacesConverter(forClass = Mitarbeiter.class)
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

    /*
     * Die Injektion von CDI Beans in Faces Converter ist leider nicht in JSF 2.2 enthalten. Mojarra 2.2.2 unterstützt
     * @Inject schon in einigen Fällen. Falls die Injektion nicht durchgeführt wurde, Bean per DeltaSpike BeanProvider holen. 
     */
    if (this.mitarbeiterRepository == null)
    {
      this.mitarbeiterRepository = BeanProvider.getContextualReference(MitarbeiterRepository.class);
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
