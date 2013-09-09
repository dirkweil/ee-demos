package de.gedoplan.buch.eedemos.provs.firma.converter;

import de.gedoplan.buch.eedemos.provs.firma.entity.Firma;
import de.gedoplan.buch.eedemos.provs.firma.repository.FirmaRepository;

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
 * Converter für {@link Firma}.
 * 
 * @author dw
 */
@FacesConverter(forClass = Firma.class)
public class FirmaConverter implements Converter
{
  @Inject
  private FirmaRepository firmaRepository;

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
    if (this.firmaRepository == null)
    {
      this.firmaRepository = BeanProvider.getContextualReference(FirmaRepository.class);
    }

    try
    {
      final Integer firmaId = Integer.valueOf(value);
      return this.firmaRepository.findById(firmaId);
    }
    catch (NumberFormatException e)
    {
      // ignore
    }

    List<Firma> firmen = this.firmaRepository.findByToStringPrefix(value);
    if (firmen.size() == 1)
    {
      return firmen.get(0);
    }

    FacesMessage msg = new FacesMessage("Unbekannte Firma: " + value);
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

    Firma firma = (Firma) value;
    return firma.getId().toString();
  }
}
