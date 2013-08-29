package de.gedoplan.buch.eedemos.provs.projekt.model;

import de.gedoplan.baselibs.enterprise.interceptor.TraceCall;
import de.gedoplan.baselibs.faces.validation.FacesValidationHelper;
import de.gedoplan.buch.eedemos.provs.common.repository.RepositoryMaster;
import de.gedoplan.buch.eedemos.provs.common.service.SessionService;
import de.gedoplan.buch.eedemos.provs.firma.entity.Firma;
import de.gedoplan.buch.eedemos.provs.firma.entity.Mitarbeiter;
import de.gedoplan.buch.eedemos.provs.firma.repository.FirmaRepository;
import de.gedoplan.buch.eedemos.provs.firma.repository.MitarbeiterRepository;
import de.gedoplan.buch.eedemos.provs.projekt.bean.ProjektLazyDataModel;
import de.gedoplan.buch.eedemos.provs.projekt.entity.Aufgabe;
import de.gedoplan.buch.eedemos.provs.projekt.entity.MitarbeiterAufgabe;
import de.gedoplan.buch.eedemos.provs.projekt.entity.Projekt;
import de.gedoplan.buch.eedemos.provs.projekt.repository.ProjektRepository;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 * Model für die Projektverwaltung.
 * 
 * @author dw
 */
@ConversationScoped
@Model
public class ProjektVerwaltungModel implements Serializable
{
  @Inject
  private SessionService        sessionService;

  @Inject
  private ProjektRepository     projektRepository;

  @Inject
  private FirmaRepository       firmaRepository;

  @Inject
  private MitarbeiterRepository mitarbeiterRepository;

  @Inject
  private RepositoryMaster      repositoryMaster;

  @Inject
  private ProjektLazyDataModel  projektDataModel;

  @Inject
  private FacesValidationHelper facesValidationHelper;

  /**
   * Aktuelles Projekt.
   */
  private Projekt               currentProjekt;

  private Aufgabe               currentAufgabe;

  private MitarbeiterAufgabe    currentMitarbeiterAufgabe;

  /**
   * Wert liefern: {@link #projektDataModel}.
   * 
   * @return Wert
   */
  public ProjektLazyDataModel getProjektDataModel()
  {
    return this.projektDataModel;
  }

  /**
   * Wert liefern: {@link #currentProjekt}.
   * 
   * @return Wert
   */
  public Projekt getCurrentProjekt()
  {
    return this.currentProjekt;
  }

  /**
   * Wert setzen: {@link #currentProjekt}.
   * 
   * @param currentProjekt Wert
   */
  public void setCurrentProjekt(Projekt currentProjekt)
  {
    if (currentProjekt.equals(this.currentProjekt))
    {
      return;
    }

    this.currentProjekt = currentProjekt;
  }

  /**
   * Tree der Aufgaben des aktuellen Projektes liefern.
   * 
   * @return Aufgaben-Tree
   */
  public TreeNode getCurrentProjektAufgabenTree()
  {
    DefaultTreeNode currentProjektAufgabenTree = new DefaultTreeNode("root", null);
    addAufgabe(currentProjektAufgabenTree, this.currentProjekt);
    return currentProjektAufgabenTree;
  }

  private void addAufgabe(TreeNode node, Aufgabe aufgabe)
  {
    TreeNode aufgabeNode = new DefaultTreeNode(aufgabe, node);
    aufgabeNode.setExpanded(true);

    for (Aufgabe teilAufgabe : aufgabe.getTeilAufgaben())
    {
      addAufgabe(aufgabeNode, teilAufgabe);
    }
  }

  /**
   * Wert liefern: {@link #currentAufgabeTreeNode}.
   * 
   * @return Wert
   */
  public Aufgabe getCurrentAufgabe()
  {
    return this.currentAufgabe;
  }

  /**
   * Wert setzen: {@link #currentAufgabeTreeNode}.
   * 
   * @param currentAufgabe Wert
   */
  public void setCurrentAufgabe(Aufgabe currentAufgabe)
  {
    this.currentAufgabe = currentAufgabe;
  }

  /**
   * Ist die aktuelle Aufgabe das Projekt?
   * 
   * @return <code>true</code>, wenn currentAufgabe und currentPprojekt gleich sind
   */
  public boolean isCurrentAufgabeEqualsProject()
  {
    return this.currentAufgabe.equals(this.currentProjekt);
  }

  /**
   * Wert liefern: {@link #currentMitarbeiterAufgabe}.
   * 
   * @return Wert
   */
  public MitarbeiterAufgabe getCurrentMitarbeiterAufgabe()
  {
    return this.currentMitarbeiterAufgabe;
  }

  /**
   * Wert setzen: {@link #currentMitarbeiterAufgabe}.
   * 
   * @param currentMitarbeiterAufgabe Wert
   */
  public void setCurrentMitarbeiterAufgabe(MitarbeiterAufgabe currentMitarbeiterAufgabe)
  {
    this.currentMitarbeiterAufgabe = currentMitarbeiterAufgabe;
  }

  /**
   * Aktionsmethode zum Anlegen eines Projekts.
   * 
   * @return Outcome
   */
  public String createProjekt()
  {
    this.sessionService.beginConversation();
    setCurrentProjekt(Projekt.createValidInstance());
    this.projektRepository.persist(this.currentProjekt);
    this.currentProjekt.clear();
    return "editProjekt";
  }

  /**
   * Aktionsmethode zum Bearbeiten eines Projekts.
   * 
   * @param projekt Projekt
   * @return Outcome
   */
  public String editProjekt(Projekt projekt)
  {
    this.sessionService.beginConversation();
    setCurrentProjekt(this.projektRepository.findById(projekt.getId()));
    return "editProjekt";
  }

  /**
   * Aktionsmethode zum Löschen eines Projekts.
   * 
   * @param projekt Projekt
   * @return Outcome
   */
  public String deleteProjekt(Projekt projekt)
  {
    this.sessionService.beginConversation();
    setCurrentProjekt(this.projektRepository.findById(projekt.getId()));
    this.projektRepository.remove(this.currentProjekt);
    return "deleteProjekt";
  }

  /**
   * Aktionsmethode zum Erzeugen einer neuen Teilaufgabe.
   * 
   * @param vaterAufgabe Vater-Aufgabe
   */
  public void createAufgabe(Aufgabe vaterAufgabe)
  {
    Aufgabe newAufgabe = vaterAufgabe.addTeilAufgabe(null, null);
    setCurrentAufgabe(newAufgabe);
  }

  /**
   * Aktionsmethode zum Bearbeiten einer Aufgabe.
   * 
   * @param aufgabe Aufgabe
   */
  public void editAufgabe(Aufgabe aufgabe)
  {
    setCurrentAufgabe(aufgabe);
  }

  /**
   * Aktionsmethode zum Löschen einer Aufgabe.
   * 
   * @param aufgabe Aufgabe
   */
  public void deleteAufgabe(Aufgabe aufgabe)
  {
    Aufgabe vaterAufgabe = aufgabe.getVaterAufgabe();
    if (vaterAufgabe != null)
    {
      vaterAufgabe.removeTeilAufgabe(aufgabe);
      setCurrentAufgabe(null);
    }
  }

  @TraceCall
  public void createMitarbeiterAufgabe()
  {
    MitarbeiterAufgabe neueMitarbeiterAufgabe = this.currentAufgabe.addMitarbeiterAufgabe(null);
    setCurrentMitarbeiterAufgabe(neueMitarbeiterAufgabe);
  }

  /**
   * Aktionsmethode zum Bearbeiten einer Mitarbeiter-Aufgabe.
   * 
   * @param mitarbeiterAufgabe Mitarbeiter-Aufgabe
   */
  public void editMitarbeiterAufgabe(MitarbeiterAufgabe mitarbeiterAufgabe)
  {
    setCurrentMitarbeiterAufgabe(mitarbeiterAufgabe);
  }

  @TraceCall
  public void deleteMitarbeiterAufgabe(MitarbeiterAufgabe mitarbeiterAufgabe)
  {
    this.currentAufgabe.removeMitarbeiterAufgabe(mitarbeiterAufgabe);
    setCurrentMitarbeiterAufgabe(null);
  }

  /**
   * Alle Änderungen speichern.
   * 
   * @return Outcome
   */
  public String save()
  {
    if (this.currentProjekt != null)
    {
      if (!this.facesValidationHelper.validate(this.currentProjekt))
      {
        return null;
      }
    }

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

  /**
   * Firmen liefern, deren String-Repräsentation mit dem angegebenen Präfix beginnt.
   * 
   * @param prefix Präfix
   * @return gefundene Firmen
   */
  public List<Firma> completeFirma(String prefix)
  {
    return this.firmaRepository.findByToStringPrefix(prefix);
  }

  /**
   * Mitarbeiter liefern, deren String-Repräsentation mit dem angegebenen Präfix beginnt.
   * 
   * @param prefix Präfix
   * @return gefundene Mitarbeiter
   */
  public List<Mitarbeiter> completeMitarbeiter(String prefix)
  {
    return this.mitarbeiterRepository.findByToStringPrefix(prefix);
  }
}
