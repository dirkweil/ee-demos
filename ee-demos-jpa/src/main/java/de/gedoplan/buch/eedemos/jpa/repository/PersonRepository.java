package de.gedoplan.buch.eedemos.jpa.repository;

import de.gedoplan.baselibs.enterprise.interceptor.TransactionRequired;
import de.gedoplan.baselibs.persistence.repository.SingleIdEntityRepository;
import de.gedoplan.buch.eedemos.jpa.entity.Person;

import javax.enterprise.context.ApplicationScoped;

/**
 * DB-Zugriffsklasse für {@link Person}.
 * 
 * @author dw
 */
@ApplicationScoped
@TransactionRequired
public class PersonRepository extends SingleIdEntityRepository<Integer, Person>
{
  /**
   * Person anhand ihrer ID finden und Lazy-Attribute nachladen.
   * 
   * @param id ID
   * @return gefundene Person oder <code>null</code>
   */
  public Person findByIdEager(Integer id)
  {
    // TODO: Load Plan!
    Person person = findById(id);
    if (person != null)
    {
      person.getHobbies().size();
      person.getMailAddresses().size();
      person.getPhones().size();
    }
    return person;
  }

}
