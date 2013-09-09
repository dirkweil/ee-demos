package de.gedoplan.buch.eedemos.provs.firma.converter;

import de.gedoplan.buch.eedemos.provs.firma.entity.Standort;
import de.gedoplan.buch.eedemos.provs.firma.repository.StandortRepository;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.deltaspike.core.api.provider.BeanProvider;

/**
 * Converter für {@link Standort}.
 * 
 * @author dw
 */
@FacesConverter(forClass = Standort.class)
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

    /*
     * Die Injektion von CDI Beans in Faces Converter ist leider nicht in JSF 2.2 enthalten. Mojarra 2.2.2 unterstützt
     * @Inject schon in einigen Fällen. Falls die Injektion nicht durchgeführt wurde, Bean per DeltaSpike BeanProvider holen. 
     */
    if (this.standortRepository == null)
    {
      this.standortRepository = BeanProvider.getContextualReference(StandortRepository.class);
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
