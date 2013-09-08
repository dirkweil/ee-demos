package de.gedoplan.buch.eedemos.provs.web.model;

import de.gedoplan.baselibs.enterprise.testdata.MasterTestDataService;
import de.gedoplan.buch.eedemos.provs.firma.entity.Firma;
import de.gedoplan.buch.eedemos.provs.firma.entity.Mitarbeiter;
import de.gedoplan.buch.eedemos.provs.firma.entity.Standort;
import de.gedoplan.buch.eedemos.provs.firma.repository.FirmaRepository;
import de.gedoplan.buch.eedemos.provs.projekt.entity.Aufgabe;
import de.gedoplan.buch.eedemos.provs.projekt.entity.MitarbeiterAufgabe;
import de.gedoplan.buch.eedemos.provs.projekt.entity.Projekt;
import de.gedoplan.buch.eedemos.provs.projekt.repository.ProjektRepository;

import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;

import org.apache.commons.logging.Log;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

//@ConversationScoped
@Model
public class PlayGroundModel //implements Serializable
{
  @Inject
  MasterTestDataService masterTestDataService;

  @Inject
  FirmaRepository       firmaRepository;

  @Inject
  ProjektRepository     projektRepository;

  @Inject
  Log                   logger;

  @Transactional
  public void createTestFixture()
  {
    this.masterTestDataService.createTestFixture(null, null);
  }

  public List<Firma> getFirmen()
  {
    return this.firmaRepository.findAll();
  }

  public TreeNode getFirmenTree()
  {
    TreeNode root = new DefaultTreeNode("Root", null);
    TreeNode firmen = new DefaultTreeNode("Firmen", root);
    for (Firma firma : getFirmen())
    {
      TreeNode firmaNode = new DefaultTreeNode(firma.toString(), firmen);
      firmaNode.setExpanded(true);

      for (Standort standort : firma.getStandorte())
      {
        new DefaultTreeNode("Standort: " + standort, firmaNode).setExpanded(true);
      }

      for (Mitarbeiter mitarbeiter : firma.getMitarbeiter())
      {
        new DefaultTreeNode("Mitarbeiter: " + mitarbeiter, firmaNode).setExpanded(true);
      }
    }

    return root;
  }

  public List<Projekt> getProjekte()
  {
    return this.projektRepository.findAll();
  }

  public TreeNode getProjekteTree()
  {
    TreeNode root = new DefaultTreeNode("Root", null);
    TreeNode projekte = new DefaultTreeNode("Projekte", root);
    for (Projekt projekt : getProjekte())
    {
      addAufgabe(projekte, projekt);
    }

    return root;
  }

  private void addAufgabe(TreeNode node, Aufgabe aufgabe)
  {
    TreeNode aufgabeNode = new DefaultTreeNode(aufgabe.toString(), node);
    aufgabeNode.setExpanded(true);

    for (MitarbeiterAufgabe mitarbeiterAufgabe : aufgabe.getMitarbeiterAufgaben())
    {
      new DefaultTreeNode("Mitarbeiter: " + mitarbeiterAufgabe.getMitarbeiter(), aufgabeNode).setExpanded(true);
    }

    for (Aufgabe teilProjekt : aufgabe.getTeilAufgaben())
    {
      addAufgabe(aufgabeNode, teilProjekt);
    }

  }

  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public String getTxStatusTransactional()
  {
    return getTxStatus();
  }

  public String getTxStatusNonTransactional()
  {
    return getTxStatus();
  }

  @Inject
  private UserTransaction userTransaction;

  private String getTxStatus()
  {
    try
    {
      switch (this.userTransaction.getStatus())
      {
      case Status.STATUS_ACTIVE:
        return "STATUS_ACTIVE";
      case Status.STATUS_COMMITTED:
        return "STATUS_COMMITTED";
      case Status.STATUS_COMMITTING:
        return "STATUS_COMMITTING";
      case Status.STATUS_MARKED_ROLLBACK:
        return "STATUS_MARKED_ROLLBACK";
      case Status.STATUS_NO_TRANSACTION:
        return "STATUS_NO_TRANSACTION";
      case Status.STATUS_PREPARED:
        return "STATUS_PREPARED";
      case Status.STATUS_PREPARING:
        return "STATUS_PREPARING";
      case Status.STATUS_ROLLEDBACK:
        return "STATUS_ROLLEDBACK";
      case Status.STATUS_ROLLING_BACK:
        return "STATUS_ROLLING_BACK";
      default:
        return "STATUS_UNKNOWN";
      }
    }
    catch (SystemException e)
    {
      return e.toString();
    }
  }
}
