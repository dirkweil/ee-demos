package de.gedoplan.buch.eedemos.provs.projekt.service;

import de.gedoplan.baselibs.enterprise.stereotype.DomainService;
import de.gedoplan.baselibs.utils.util.Tree;
import de.gedoplan.buch.eedemos.provs.firma.entity.Person;
import de.gedoplan.buch.eedemos.provs.projekt.entity.Aufgabe;
import de.gedoplan.buch.eedemos.provs.projekt.entity.MitarbeiterAufgabe;
import de.gedoplan.buch.eedemos.provs.projekt.entity.ProjektZeit;
import de.gedoplan.buch.eedemos.provs.projekt.repository.MitarbeiterAufgabeRepository;
import de.gedoplan.buch.eedemos.provs.projekt.repository.ProjektZeitRepository;
import de.gedoplan.buch.eedemos.provs.projekt.util.AufgabeMitarbeiteraufgabeProjektzeitHolder;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

/**
 * ProjektZeit-Service.
 * 
 * @author dw
 */
@DomainService
public class ProjektZeitService
{
  @Inject
  private MitarbeiterAufgabeRepository mitarbeiterAufgabeRepository;

  @Inject
  private ProjektZeitRepository        projektZeitRepository;

  /**
   * Tree mit Aufgaben, MitarbeiterAufgaben und ProjektZeiten zu einer Person erstellen.
   * 
   * @param person Person
   * @param start Starttermin; falls nicht <code>null</code>, muss er im Limit der MA-Aufgabe liegen
   * @param ende Endtermin; falls nicht <code>null</code>, muss er im Limit der MA-Aufgabe liegen
   * @param inclusiveInaktiv <code>true</code>, wenn auch inaktive MA-Aufgaben gefunen werden sollen
   * @return erzeugte Tree-Struktur
   */
  public Tree<AufgabeMitarbeiteraufgabeProjektzeitHolder> createAufgabenTree(Person person, Date start, Date ende, boolean inclusiveInaktiv)
  {
    List<MitarbeiterAufgabe> mitarbeiterAufgaben = this.mitarbeiterAufgabeRepository.findByPerson(person, start, ende, inclusiveInaktiv);

    Tree<AufgabeMitarbeiteraufgabeProjektzeitHolder> tree = new Tree<AufgabeMitarbeiteraufgabeProjektzeitHolder>(null);

    for (MitarbeiterAufgabe mitarbeiterAufgabe : mitarbeiterAufgaben)
    {
      // Aufgabe in Baum einfügen
      Tree<AufgabeMitarbeiteraufgabeProjektzeitHolder> treeNode = add(tree, mitarbeiterAufgabe.getAufgabe());

      // Bislang ist im neuen Knoten nur die Aufgabe gesetzt; MitarbeiterAufgabe dazu setzen
      AufgabeMitarbeiteraufgabeProjektzeitHolder treeData = treeNode.getData();
      treeData.setMitarbeiterAufgabe(mitarbeiterAufgabe);

      // Projektzeiten für den Datumsbereich zunächst leer besetzen (als Platzhalter für jeden Tag aufsteigend)
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(start);
      clearTime(calendar);
      Calendar endeCalendar = Calendar.getInstance();
      endeCalendar.setTime(ende);
      clearTime(endeCalendar);
      if (!endeCalendar.before(calendar))
      {
        do
        {
          treeData.getProjektZeiten().add(new ProjektZeit(calendar.getTime(), mitarbeiterAufgabe, 0));
          calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        while (!calendar.after(endeCalendar));
      }

      // Projektzeiten der Mitarbeiteraufgabe einsetzen - überschreiben die eben erzeugten Platzhalter
      List<ProjektZeit> projektZeiten = this.projektZeitRepository.findByMitarbeiterAufgabe(mitarbeiterAufgabe, start, ende);
      for (ProjektZeit projektZeit : projektZeiten)
      {
        calendar.setTime(projektZeit.getBuchungstag());
        int idx = calendar.get(Calendar.DAY_OF_MONTH) - 1;
        treeData.getProjektZeiten().set(idx, projektZeit);
      }
    }

    return tree;
  }

  /**
   * Knoten für Aufgabe im Baum suchen und hinzufügen, wenn nötig.
   * 
   * @param tree Baum
   * @param aufgabe Aufgabe
   * @return gefundener oder neu eingefügter Knoten
   */
  private Tree<AufgabeMitarbeiteraufgabeProjektzeitHolder> add(Tree<AufgabeMitarbeiteraufgabeProjektzeitHolder> tree, Aufgabe aufgabe)
  {
    // Rekursion abbrechen, wenn aufgabe null
    if (aufgabe == null)
    {
      return null;
    }

    // Falls aufgabe bereits im tree, entsprechenden Knoten liefern
    Tree<AufgabeMitarbeiteraufgabeProjektzeitHolder> treeNode = find(tree, aufgabe);
    if (treeNode != null)
    {
      return treeNode;
    }

    // aufgabe ist noch nicht im tree; neuen Knoten erstellen
    AufgabeMitarbeiteraufgabeProjektzeitHolder data = new AufgabeMitarbeiteraufgabeProjektzeitHolder(aufgabe, null);
    treeNode = new Tree<AufgabeMitarbeiteraufgabeProjektzeitHolder>(data);

    // Falls aufgabe eine Vateraufgabe hat, neuen Knoten dort einketten, sonst an der Wurzel des Baums
    Tree<AufgabeMitarbeiteraufgabeProjektzeitHolder> vaterTreeNode = add(tree, aufgabe.getVaterAufgabe());
    if (vaterTreeNode == null)
    {
      vaterTreeNode = tree;
    }
    vaterTreeNode.getChildren().add(treeNode);

    return treeNode;
  }

  /**
   * Knoten im Baum für Aufgabe suchen.
   * 
   * Der Wurzelknoten wird nicht berücksichtigt!
   * 
   * @param tree Baum
   * @param aufgabe Aufgabe
   * @return gefundener Knoten oder <code>null</code>
   */
  private Tree<AufgabeMitarbeiteraufgabeProjektzeitHolder> find(Tree<AufgabeMitarbeiteraufgabeProjektzeitHolder> tree, Aufgabe aufgabe)
  {
    for (Tree<AufgabeMitarbeiteraufgabeProjektzeitHolder> child : tree.getChildren())
    {
      AufgabeMitarbeiteraufgabeProjektzeitHolder treeData = child.getData();
      if (aufgabe.equals(treeData.getAufgabe()))
      {
        return child;
      }

      return find(child, aufgabe);
    }

    return null;
  }

  private static void clearTime(Calendar calendar)
  {
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
  }

  /**
   * Änderungen an den Projektzeiten persistent machen.
   * 
   * Im übergebenen Aufgabenbaum interessieren nur die Knoten, die eine MitarbeiterAufgabe und damit Projektzeiten enthalten.
   * 
   * @param tree Aufgaben-Baum
   */
  public void persistModifications(Tree<AufgabeMitarbeiteraufgabeProjektzeitHolder> tree)
  {
    // Nur Knoten mit ausgefüllter MitarbeiterAufgabe berücksichtigen
    AufgabeMitarbeiteraufgabeProjektzeitHolder data = tree.getData();
    if (data != null && data.getMitarbeiterAufgabe() != null)
    {
      for (ProjektZeit projektZeit : data.getProjektZeiten())
      {
        if (projektZeit.getMinuten() != 0)
        {
          // Einträge mit Zeiten != 0 persistieren (schadet nicht, wenn Eintrag bereits persistent)
          this.projektZeitRepository.persist(projektZeit);
        }
        else
        {
          // Einträge mit Zeiten == 0 löschen (schadet nicht, wenn Eintrag nicht persistent)
          this.projektZeitRepository.remove(projektZeit);
        }
      }
    }

    // Rekursion
    for (Tree<AufgabeMitarbeiteraufgabeProjektzeitHolder> child : tree.getChildren())
    {
      persistModifications(child);
    }
  }

}
