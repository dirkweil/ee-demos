package de.gedoplan.buch.eedemos.provs.firma.converter;

import de.gedoplan.buch.eedemos.provs.firma.entity.Land;
import de.gedoplan.buch.eedemos.provs.firma.repository.LandRepository;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

/**
 * Converter für {@link Land}.
 * 
 * @author dw
 */
@FacesConverter(forClass = Land.class)
@RequestScoped
public class LandConverter implements Converter
{
  @Inject
  private LandRepository landRepository;

  @Override
  public Object getAsObject(FacesContext context, UIComponent component, String value)
  {
    if (value == null)
    {
      return "";
    }

    return this.landRepository.findById(value);
  }

  @Override
  public String getAsString(FacesContext context, UIComponent component, Object value)
  {
    if ("".equals(value))
    {
      return null;
    }

    Land land = (Land) value;
    return land.getId();
  }

}
