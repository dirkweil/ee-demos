package de.gedoplan.buch.eedemos.provs.projekt.repository;

import de.gedoplan.baselibs.enterprise.stereotype.DataRepository;
import de.gedoplan.baselibs.persistence.repository.SingleIdEntityRepository;
import de.gedoplan.buch.eedemos.provs.firma.entity.Mitarbeiter_;
import de.gedoplan.buch.eedemos.provs.firma.entity.Person;
import de.gedoplan.buch.eedemos.provs.projekt.entity.Limit;
import de.gedoplan.buch.eedemos.provs.projekt.entity.Limit_;
import de.gedoplan.buch.eedemos.provs.projekt.entity.MitarbeiterAufgabe;
import de.gedoplan.buch.eedemos.provs.projekt.entity.MitarbeiterAufgabe_;
import de.gedoplan.buch.eedemos.provs.projekt.entity.Projekt;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Data-Repository für MitarbeiterAufgabe.
 * 
 * @author dw
 */
@DataRepository
public class MitarbeiterAufgabeRepository extends SingleIdEntityRepository<Integer, Projekt>
{
  private static final long serialVersionUID = 1L;

  /**
   * Personen finden, die aktiven Mitarbeiteraufgaben zugeordnet sind.
   * 
   * @return gefundene Personen
   */
  public List<Person> findPersonenMitaktivenAufgaben()
  {
    CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
    CriteriaQuery<Person> criteriaQuery = criteriaBuilder.createQuery(Person.class);

    Root<MitarbeiterAufgabe> mitarbeiterAufgabe = criteriaQuery.from(MitarbeiterAufgabe.class);
    Path<Person> person = mitarbeiterAufgabe.get(MitarbeiterAufgabe_.mitarbeiter).get(Mitarbeiter_.person);

    Predicate mitarbeiterAufgabeAktiv = criteriaBuilder.isFalse(mitarbeiterAufgabe.get(MitarbeiterAufgabe_.inaktiv));

    return findMulti(criteriaQuery.select(person).distinct(true).where(mitarbeiterAufgabeAktiv));
  }

  /**
   * Mitarbeiteraufgaben zu einer Person finden.
   * 
   * @param person Person
   * @param monat Monat; falls nicht <code>null</code>, muss er im Limit der MA-Aufgabe liegen
   * @param inclusiveInaktiv <code>true</code>, wenn auch inaktive MA-Aufgaben gefunen werden sollen
   * @return gefundene MA-Aufgaben
   */
  @Deprecated
  public List<MitarbeiterAufgabe> findByPerson(Person person, Date monat, boolean inclusiveInaktiv)
  {
    CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
    CriteriaQuery<MitarbeiterAufgabe> criteriaQuery = criteriaBuilder.createQuery(MitarbeiterAufgabe.class);

    Root<MitarbeiterAufgabe> maAufgabe = criteriaQuery.from(MitarbeiterAufgabe.class);
    Path<Person> maAufgabePerson = maAufgabe.get(MitarbeiterAufgabe_.mitarbeiter).get(Mitarbeiter_.person);

    // Je nach den übergebenen Parametern werden 1-3 Bedingungen gebraucht
    int predicateAnzahl = 1;
    if (monat != null)
    {
      ++predicateAnzahl;
    }
    if (!inclusiveInaktiv)
    {
      ++predicateAnzahl;
    }
    Predicate[] predicate = new Predicate[predicateAnzahl];

    // Bedingung für aktive Aufgaben
    if (!inclusiveInaktiv)
    {
      predicate[--predicateAnzahl] = criteriaBuilder.isFalse(maAufgabe.get(MitarbeiterAufgabe_.inaktiv));
    }

    // Bedingung für Monat im Limit
    final String parmNameMonatsBeginn = "monatsBeginn";
    final String parmNameMonatsUltimo = "monatsUltimo";
    if (monat != null)
    {
      Path<Limit> maAufgabeLimit = maAufgabe.get(MitarbeiterAufgabe_.limit);
      Path<Date> maAufgabeStart = maAufgabeLimit.get(Limit_.start);
      Path<Date> maAufgabeEnde = maAufgabeLimit.get(Limit_.ende);
      ParameterExpression<Date> parmMonatsBeginn = criteriaBuilder.parameter(Date.class, parmNameMonatsBeginn);
      ParameterExpression<Date> parmMonatsUltimo = criteriaBuilder.parameter(Date.class, parmNameMonatsUltimo);

      // Bedingung bzgl. Start: Aufgabenstart null oder <= monatsUltimo
      Predicate abStart = criteriaBuilder.or(criteriaBuilder.isNull(maAufgabeStart), criteriaBuilder.lessThanOrEqualTo(maAufgabeStart, parmMonatsUltimo));

      // Bedingung bzgl. Ende: Aufgabenende null oder >= monatsBeginn
      Predicate bisEnde = criteriaBuilder.or(criteriaBuilder.isNull(maAufgabeEnde), criteriaBuilder.greaterThanOrEqualTo(maAufgabeEnde, parmMonatsBeginn));

      predicate[--predicateAnzahl] = criteriaBuilder.and(abStart, bisEnde);
    }

    // Bedingung für Person
    final String parmNamePerson = "person";
    predicate[--predicateAnzahl] = criteriaBuilder.equal(maAufgabePerson, criteriaBuilder.parameter(Person.class, parmNamePerson));

    // Query zusammensetzen, Parameterwerte einfüllen und ausführen
    TypedQuery<MitarbeiterAufgabe> query = this.entityManager.createQuery(criteriaQuery.select(maAufgabe).where(predicate));
    if (monat != null)
    {
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(monat);
      calendar.set(Calendar.DAY_OF_MONTH, 1);
      calendar.set(Calendar.HOUR_OF_DAY, 0);
      calendar.set(Calendar.MINUTE, 0);
      calendar.set(Calendar.SECOND, 0);
      calendar.set(Calendar.MILLISECOND, 0);
      query.setParameter(parmNameMonatsBeginn, calendar.getTime(), TemporalType.DATE);

      calendar.add(Calendar.MONTH, 1);
      calendar.add(Calendar.MILLISECOND, -1);
      query.setParameter(parmNameMonatsUltimo, calendar.getTime(), TemporalType.DATE);
    }
    query.setParameter(parmNamePerson, person);
    return findMulti(query);
  }

  /**
   * Mitarbeiteraufgaben zu einer Person finden.
   * 
   * @param person Person
   * @param start Startdatum; falls nicht <code>null</code>, muss es im Limit der MA-Aufgabe liegen
   * @param ende Endedatum; falls nicht <code>null</code>, muss es im Limit der MA-Aufgabe liegen
   * @param inclusiveInaktiv <code>true</code>, wenn auch inaktive MA-Aufgaben gefunen werden sollen
   * @return gefundene MA-Aufgaben
   */
  public List<MitarbeiterAufgabe> findByPerson(Person person, Date start, Date ende, boolean inclusiveInaktiv)
  {
    CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
    CriteriaQuery<MitarbeiterAufgabe> criteriaQuery = criteriaBuilder.createQuery(MitarbeiterAufgabe.class);

    Root<MitarbeiterAufgabe> maAufgabe = criteriaQuery.from(MitarbeiterAufgabe.class);
    Path<Person> maAufgabePerson = maAufgabe.get(MitarbeiterAufgabe_.mitarbeiter).get(Mitarbeiter_.person);

    // Bedingung für Person
    final String parmNamePerson = "person";
    Predicate where = criteriaBuilder.equal(maAufgabePerson, criteriaBuilder.parameter(Person.class, parmNamePerson));

    // Bedingung für Start: Aufgabenende null oder nach start
    final String parmNameStart = "start";
    final String parmNameEnde = "ende";
    if (start != null)
    {
      Path<Date> maAufgabeEnde = maAufgabe.get(MitarbeiterAufgabe_.limit).get(Limit_.ende);
      ParameterExpression<Date> parmStart = criteriaBuilder.parameter(Date.class, parmNameStart);

      Predicate nachStart = criteriaBuilder.or(criteriaBuilder.isNull(maAufgabeEnde), criteriaBuilder.greaterThanOrEqualTo(maAufgabeEnde, parmStart));

      where = criteriaBuilder.and(where, nachStart);
    }

    // Bedingung für Ende: Aufgabenstart null oder vor ende
    if (ende != null)
    {
      Path<Date> maAufgabeStart = maAufgabe.get(MitarbeiterAufgabe_.limit).get(Limit_.start);
      ParameterExpression<Date> parmEnde = criteriaBuilder.parameter(Date.class, parmNameEnde);

      Predicate vorEnde = criteriaBuilder.or(criteriaBuilder.isNull(maAufgabeStart), criteriaBuilder.lessThanOrEqualTo(maAufgabeStart, parmEnde));

      where = criteriaBuilder.and(where, vorEnde);
    }

    // Bedingung für aktive Aufgaben
    if (!inclusiveInaktiv)
    {
      Predicate nichtInaktiv = criteriaBuilder.isFalse(maAufgabe.get(MitarbeiterAufgabe_.inaktiv));
      where = criteriaBuilder.and(where, nichtInaktiv);
    }

    // Query zusammensetzen, Parameterwerte einfüllen und ausführen
    TypedQuery<MitarbeiterAufgabe> query = this.entityManager.createQuery(criteriaQuery.select(maAufgabe).where(where));
    query.setParameter(parmNamePerson, person);
    if (start != null)
    {
      query.setParameter(parmNameStart, start, TemporalType.DATE);
    }
    if (ende != null)
    {
      query.setParameter(parmNameEnde, ende, TemporalType.DATE);
    }
    return findMulti(query);
  }
}
