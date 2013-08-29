package de.gedoplan.buch.eedemos.provs.projekt.model;

import de.gedoplan.baselibs.utils.util.ApplicationTime;
import de.gedoplan.buch.eedemos.provs.common.qualifier.Angemeldet;
import de.gedoplan.buch.eedemos.provs.common.qualifier.Current;
import de.gedoplan.buch.eedemos.provs.firma.entity.Person;
import de.gedoplan.buch.eedemos.provs.projekt.qualifier.Zeiterfassung;
import de.gedoplan.buch.eedemos.provs.projekt.repository.MitarbeiterAufgabeRepository;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

/**
 * Model für die Zeiterfassung - Auswahl von Buchungsmonat und Person.
 * 
 * @author dw
 */
@SessionScoped
@Model
public class ZeiterfassungAuswahlModel implements Serializable
{
  @Inject
  private MitarbeiterAufgabeRepository mitarbeiterAufgabeRepository;

  /**
   * Buchungsmonat.
   */
  private Date                         buchungsMonat;

  /**
   * Person, für die gebucht wird.
   */
  @Inject
  @Current
  @Angemeldet
  private Person                       buchungsPerson;

  @PostConstruct
  private void init()
  {
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(ApplicationTime.getCurrentMillis());
    if (cal.get(Calendar.DAY_OF_MONTH) < 5)
    {
      cal.roll(Calendar.MONTH, false);
    }
    this.buchungsMonat = cal.getTime();
  }

  /**
   * Wert liefern: {@link #buchungsMonat}.
   * 
   * @return Wert
   */
  @Produces
  @Current
  @Zeiterfassung
  public Date getBuchungsMonat()
  {
    return this.buchungsMonat;
  }

  /**
   * Wert setzen: {@link #buchungsMonat}.
   * 
   * @param buchungsmonat Wert
   */
  public void setBuchungsMonat(Date buchungsmonat)
  {
    this.buchungsMonat = buchungsmonat;
  }

  /**
   * Wert liefern: {@link #buchungsPerson}.
   * 
   * @return Wert
   */
  @Produces
  @Current
  @Zeiterfassung
  public Person getBuchungsPerson()
  {
    return this.buchungsPerson;
  }

  /**
   * Wert setzen: {@link #buchungsPerson}.
   * 
   * @param buchungsPerson Wert
   */
  public void setBuchungsPerson(Person buchungsPerson)
  {
    this.buchungsPerson = buchungsPerson;
  }

  /**
   * Personen liefern, deren String-Repräsentation mit dem angegebenen Präfix beginnt.
   * 
   * @param prefix Präfix
   * @return gefundene Personen, sortiert
   */
  public List<Person> completeBuchungsPerson(String prefix)
  {
    // Personen mit aktiven Aufgaben suchen
    List<Person> personen = this.mitarbeiterAufgabeRepository.findPersonenMitaktivenAufgaben();

    // Buchungsperson hinzufügen, wenn nicht schon drin
    if (!personen.contains(this.buchungsPerson))
    {
      personen.add(this.buchungsPerson);
    }

    // Personen entfernen, die nicht zum Präfix passen
    Iterator<Person> iter = personen.iterator();
    while (iter.hasNext())
    {
      Person person = iter.next();
      if (!person.toString().startsWith(prefix))
      {
        iter.remove();
      }
    }

    Collections.sort(personen);
    return personen;
  }

}
