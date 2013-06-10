package de.gedoplan.buch.eedemos.provs.firma.repository;

import de.gedoplan.baselibs.enterprise.stereotype.DataRepository;
import de.gedoplan.baselibs.persistence.repository.SingleIdEntityRepository;
import de.gedoplan.buch.eedemos.provs.firma.entity.Firma;
import de.gedoplan.buch.eedemos.provs.firma.entity.Firma_;

import java.util.List;

/**
 * Data-Repository für Firma.
 * 
 * @author dw
 */
@DataRepository
public class FirmaRepository extends SingleIdEntityRepository<Integer, Firma>
{
  /**
   * Firma anhand ihres Namens finden.
   * 
   * @param name Name
   * @return gefundene Firma oder <code>null</code>
   */
  public Firma findByName(String name)
  {
    return findSingleByProperty(Firma_.name, name);
  }

  /**
   * Firmen suchen, deren String-Repräsentation {@link Firma#toString()} mit dem angegebenen Präfix beginnt.
   * 
   * @param prefix Präfix
   * @return gefundene Firmen
   */
  public List<Firma> findByToStringPrefix(String prefix)
  {
    return findMultiByToStringPrefix(prefix, Firma_.name);
  }

}
