package de.gedoplan.buch.eedemos.provs.firma.repository;

import de.gedoplan.baselibs.enterprise.stereotype.DataRepository;
import de.gedoplan.baselibs.persistence.repository.SingleIdEntityRepository;
import de.gedoplan.buch.eedemos.provs.firma.entity.Person;
import de.gedoplan.buch.eedemos.provs.firma.entity.Person_;

import java.util.List;

/**
 * Data-Repository für Person.
 * 
 * @author dw
 */
@DataRepository
public class PersonRepository extends SingleIdEntityRepository<Integer, Person>
{
  private static final long serialVersionUID = 1L;

  /**
   * Person anhand ihrer User-ID suchen.
   * 
   * @param userId User-ID
   * @return gefundene Person oder <code>null</code>
   */
  public Person findByUserId(String userId)
  {
    return findSingleByProperty(Person_.userId, userId);
  }

  /**
   * Personen suchen, deren String-Repräsentation {@link Person#toString()} mit dem angegebenen Präfix beginnt.
   * 
   * @param prefix Präfix
   * @return gefundene Personen
   */
  public List<Person> findByToStringPrefix(String prefix)
  {
    return findMultiByToStringPrefix(prefix, Person_.name);
  }
}
