package de.gedoplan.buch.eedemos.provs.firma.converter;

import de.gedoplan.buch.eedemos.provs.firma.entity.Person;
import de.gedoplan.buch.eedemos.provs.firma.repository.PersonRepository;

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
 * Converter für {@link Person}.
 * 
 * @author dw
 */
@FacesConverter(forClass = Person.class)
public class PersonConverter implements Converter
{
  @Inject
  private PersonRepository personRepository;

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
    if (this.personRepository == null)
    {
      this.personRepository = BeanProvider.getContextualReference(PersonRepository.class);
    }

    try
    {
      final Integer personId = Integer.valueOf(value);
      return this.personRepository.findById(personId);
    }
    catch (NumberFormatException e)
    {
      // ignore
    }

    List<Person> personen = this.personRepository.findByToStringPrefix(value);
    if (personen.size() == 1)
    {
      return personen.get(0);
    }

    FacesMessage msg = new FacesMessage("Unbekannte Person: " + value);
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

    Person person = (Person) value;
    return person.getId().toString();
  }
}
