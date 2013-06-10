package de.gedoplan.buch.eedemos.provs.projekt.model;

import de.gedoplan.baselibs.enterprise.faces.validation.FacesValidationHelper;
import de.gedoplan.baselibs.utils.util.Tree;
import de.gedoplan.buch.eedemos.provs.common.qualifier.Current;
import de.gedoplan.buch.eedemos.provs.common.repository.RepositoryMaster;
import de.gedoplan.buch.eedemos.provs.common.service.SessionService;
import de.gedoplan.buch.eedemos.provs.firma.entity.Person;
import de.gedoplan.buch.eedemos.provs.projekt.qualifier.Zeiterfassung;
import de.gedoplan.buch.eedemos.provs.projekt.service.ProjektZeitService;
import de.gedoplan.buch.eedemos.provs.projekt.util.AufgabeMitarbeiteraufgabeProjektzeitHolder;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 * Model für die Zeiterfassung - Eingabe von Zeiten.
 * 
 * @author dw
 */
@ConversationScoped
@Model
public class ZeiterfassungEingabeModel implements Serializable
{
  @Inject
  private SessionService                                   sessionService;

  @Inject
  private ProjektZeitService                               projektZeitService;

  @Inject
  private RepositoryMaster                                 repositoryMaster;

  @Inject
  private FacesValidationHelper                            facesValidationHelper;

  /**
   * Buchungsmonat (Anfang).
   */
  private Date                                             buchungsMonat;

  /**
   * Buchungsmonat (Ultimo).
   */
  private Date                                             buchungsMonatUltimo;

  /**
   * Hilfsobjekt für Operationen im aktuellen Monat. Dieses Objekt wird mit Jahr und Monat des Buchunngsmonats besetzt. Die
   * restlichen Felder darin werden von diversen Methoden modifiziert.
   */
  private Calendar                                         buchungsMonatAsCalendar = Calendar.getInstance();

  private static final DateFormat                          FORMAT_DAY_XX           = new SimpleDateFormat("EE");

  /**
   * User.
   */
  @Inject
  @Current
  @Zeiterfassung
  private Person                                           buchungsPerson;

  private Tree<AufgabeMitarbeiteraufgabeProjektzeitHolder> aufgabenTree;

  private TreeNode                                         treeTableAufgaben;

  /**
   * Wert liefern: {@link #buchungsMonat}.
   * 
   * @return Wert
   */
  public Date getBuchungsMonat()
  {
    return this.buchungsMonat;
  }

  /**
   * Wert setzen: {@link #buchungsMonat}.
   * 
   * @param buchungsMonat Wert
   */
  @Inject
  protected void setBuchungsMonat(@Current @Zeiterfassung Date buchungsMonat)
  {
    this.buchungsMonatAsCalendar.setTime(buchungsMonat);
    this.buchungsMonatAsCalendar.set(Calendar.DAY_OF_MONTH, 1);
    this.buchungsMonatAsCalendar.set(Calendar.HOUR_OF_DAY, 0);
    this.buchungsMonatAsCalendar.set(Calendar.MINUTE, 0);
    this.buchungsMonatAsCalendar.set(Calendar.SECOND, 0);
    this.buchungsMonatAsCalendar.set(Calendar.MILLISECOND, 0);
    this.buchungsMonat = this.buchungsMonatAsCalendar.getTime();

    this.buchungsMonatAsCalendar.add(Calendar.MONTH, 1);
    this.buchungsMonatAsCalendar.add(Calendar.MILLISECOND, -1);
    this.buchungsMonatUltimo = this.buchungsMonatAsCalendar.getTime();
  }

  /**
   * Anzahl Tage im Buchungsmonat liefern.
   * 
   * @return Anzahl Tage
   */
  public int getTageInBuchungsMonat()
  {
    return this.buchungsMonatAsCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
  }

  /**
   * Wochentag ermitteln.
   * 
   * @param tag Tag im Monat
   * @return Wochentag (Kürzel)
   */
  public String getWochenTag(int tag)
  {
    this.buchungsMonatAsCalendar.set(Calendar.DAY_OF_MONTH, tag);
    return FORMAT_DAY_XX.format(this.buchungsMonatAsCalendar.getTime());
  }

  /**
   * Wert liefern: {@link #buchungsPerson}.
   * 
   * @return Wert
   */
  public Person getBuchungsPerson()
  {
    return this.buchungsPerson;
  }

  /**
   * Wert liefern: {@link #treeTableAufgaben}.
   * 
   * @return Wert
   */
  public TreeNode getTreeTableAufgaben()
  {
    return this.treeTableAufgaben;
  }

  /**
   * Aktionsmethode zum Beginn der Erfassung der Zeiten eines Monats.
   * 
   * @return Outcome
   */
  public String begin()
  {
    // Aufgaben für Buchungs-Person und Monat zusammenstellen lassen
    this.aufgabenTree = this.projektZeitService.createAufgabenTree(this.buchungsPerson, this.buchungsMonat, this.buchungsMonatUltimo, false);

    // Den Aufgaben-Baum in die PrimeFaces-Variante umbauen
    this.treeTableAufgaben = new DefaultTreeNode("root", null);
    treeConvert(this.aufgabenTree, this.treeTableAufgaben);

    this.sessionService.beginConversation();
    return "editZeiten";
  }

  private void treeConvert(Tree<AufgabeMitarbeiteraufgabeProjektzeitHolder> fromTree, TreeNode toTree)
  {
    for (Tree<AufgabeMitarbeiteraufgabeProjektzeitHolder> fromTreeNode : fromTree.getChildren())
    {
      DefaultTreeNode toTreeNode = new DefaultTreeNode(fromTreeNode.getData(), toTree);
      toTree.setExpanded(true);
      treeConvert(fromTreeNode, toTreeNode);
    }
  }

  /**
   * Alle Änderungen speichern.
   * 
   * @return Outcome
   */
  public String save()
  {
    // Änderungen persistent machen
    this.projektZeitService.persistModifications(this.aufgabenTree);

    try
    {
      this.repositoryMaster.saveAll();
      this.sessionService.endConversation();
      return "saved";
    }
    catch (Throwable e) // CHECKSTYLE:IGNORE
    {
      if (this.facesValidationHelper.convertToFacesMessages(e) == 0)
      {
        throw e;
      }
    }

    FacesContext.getCurrentInstance().validationFailed();
    return null;
  }

  /**
   * Alle Änderungen verwerfen.
   * 
   * @return Outcome
   */
  public String cancel()
  {
    this.sessionService.endConversation();
    return "cancelled";
  }
}
