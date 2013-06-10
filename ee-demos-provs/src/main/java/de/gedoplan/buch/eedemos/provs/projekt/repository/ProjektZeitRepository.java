package de.gedoplan.buch.eedemos.provs.projekt.repository;

import de.gedoplan.baselibs.enterprise.stereotype.DataRepository;
import de.gedoplan.baselibs.persistence.repository.SingleIdEntityRepository;
import de.gedoplan.buch.eedemos.provs.projekt.entity.MitarbeiterAufgabe;
import de.gedoplan.buch.eedemos.provs.projekt.entity.ProjektZeit;
import de.gedoplan.buch.eedemos.provs.projekt.entity.ProjektZeit_;

import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Data-Repository für ProjektZeit.
 * 
 * @author dw
 */
@DataRepository
public class ProjektZeitRepository extends SingleIdEntityRepository<Integer, ProjektZeit>
{
  /**
   * Projektzeiten für Mitarbeiteraufgabe finden.
   * 
   * @param mitarbeiterAufgabe MA-Aufgabe
   * @param start Tag, ab dem gesucht wird (inklusive); <code>null</code> für unbegrenzt
   * @param ende Tag, bis zu dem gesucht wird (inklusive); <code>null</code> für unbegrenzt
   * @return gefundene Projektzeiten
   */
  public List<ProjektZeit> findByMitarbeiterAufgabe(MitarbeiterAufgabe mitarbeiterAufgabe, Date start, Date ende)
  {
    CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
    CriteriaQuery<ProjektZeit> criteriaQuery = criteriaBuilder.createQuery(ProjektZeit.class);

    Root<ProjektZeit> prjZeit = criteriaQuery.from(ProjektZeit.class);
    Join<ProjektZeit, MitarbeiterAufgabe> maAufgabe = prjZeit.join(ProjektZeit_.mitarbeiterAufgabe);
    Path<Date> prjZeitTag = prjZeit.get(ProjektZeit_.buchungstag);

    // Where-Bedingung zusammenstückeln
    final String parmNameMitarbeiterAufgabe = "mitarbeiterAufgabe";
    ParameterExpression<MitarbeiterAufgabe> parmMitarbeiterAufgabe = criteriaBuilder.parameter(MitarbeiterAufgabe.class, parmNameMitarbeiterAufgabe);
    Predicate whereBedingung = criteriaBuilder.equal(maAufgabe, parmMitarbeiterAufgabe);

    final String parmNameBeginn = "beginn";
    if (start != null)
    {
      ParameterExpression<Date> parmBeginn = criteriaBuilder.parameter(Date.class, parmNameBeginn);
      whereBedingung = criteriaBuilder.and(whereBedingung, criteriaBuilder.greaterThanOrEqualTo(prjZeitTag, parmBeginn));
    }

    final String parmNameEnde = "ende";
    if (ende != null)
    {
      ParameterExpression<Date> parmEnde = criteriaBuilder.parameter(Date.class, parmNameEnde);
      whereBedingung = criteriaBuilder.and(whereBedingung, criteriaBuilder.lessThanOrEqualTo(prjZeitTag, parmEnde));
    }

    // Query zusammensetzen, Parameterwerte einfüllen und ausführen
    TypedQuery<ProjektZeit> query = this.entityManager.createQuery(criteriaQuery.select(prjZeit).where(whereBedingung));
    query.setParameter(parmNameMitarbeiterAufgabe, mitarbeiterAufgabe);
    if (start != null)
    {
      query.setParameter(parmNameBeginn, start, TemporalType.DATE);
    }
    if (ende != null)
    {
      query.setParameter(parmNameEnde, ende, TemporalType.DATE);
    }
    return findMulti(query);
  }

  /**
   * Projektzeit für Mitarbeiteraufgabe und Tag finden.
   * 
   * @param mitarbeiterAufgabe MA-Aufgabe
   * @param tag Tag
   * @return gefunden Projektzeit
   */
  public ProjektZeit findByMitarbeiterAufgabe(MitarbeiterAufgabe mitarbeiterAufgabe, Date tag)
  {
    List<ProjektZeit> projektZeiten = findByMitarbeiterAufgabe(mitarbeiterAufgabe, tag, tag);
    switch (projektZeiten.size())
    {
    case 0:
      throw new NoResultException();
    case 1:
      return projektZeiten.get(0);
    default:
      throw new NonUniqueResultException();
    }
  }
}
