package de.gedoplan.buch.eedemos.provs.firma.repository;

import de.gedoplan.baselibs.enterprise.stereotype.DataRepository;
import de.gedoplan.baselibs.persistence.repository.SingleIdEntityRepository;
import de.gedoplan.buch.eedemos.provs.firma.entity.Firma;
import de.gedoplan.buch.eedemos.provs.firma.entity.Mitarbeiter;
import de.gedoplan.buch.eedemos.provs.firma.entity.Mitarbeiter_;
import de.gedoplan.buch.eedemos.provs.firma.entity.Person_;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Data-Repository für Mitarbeiter.
 * 
 * @author dw
 */
@DataRepository
public class MitarbeiterRepository extends SingleIdEntityRepository<Integer, Mitarbeiter>
{

  /**
   * Mitarbeiter anhand von Firma, Name und Vorname suchen.
   * 
   * @param firma Firma
   * @param mitarbeiterName Name
   * @param mitarbeiterVorname Vorname
   * @return Liste der gefundenen Mitarbeiter (kann leer sein)
   */
  public List<Mitarbeiter> findByFirmaAndName(Firma firma, String mitarbeiterName, String mitarbeiterVorname)
  {
    CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
    CriteriaQuery<Mitarbeiter> query = builder.createQuery(Mitarbeiter.class);

    Root<Mitarbeiter> root = query.from(Mitarbeiter.class);
    Predicate eqFirma = builder.equal(root.get(Mitarbeiter_.firma), firma);
    Predicate eqName = builder.equal(root.get(Mitarbeiter_.person).get(Person_.name), mitarbeiterName);
    Predicate eqVorname = builder.equal(root.get(Mitarbeiter_.person).get(Person_.vorname), mitarbeiterVorname);

    query.where(eqFirma, eqName, eqVorname);

    return findMulti(query);
  }

  /**
   * Mitarbeiter liefern, deren String-Repräsentation mit dem angegebenen Präfix beginnt.
   * 
   * @param prefix Präfix
   * @return gefundene Mitarbeiter
   */
  public List<Mitarbeiter> findByToStringPrefix(String prefix)
  {
    return findMultiByToStringPrefix(prefix, Mitarbeiter_.person, Person_.name);
  }

}
