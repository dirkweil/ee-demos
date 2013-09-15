package de.gedoplan.buch.eedemos.provs.web.model;

import de.gedoplan.baselibs.enterprise.testdata.MasterTestDataService;
import de.gedoplan.buch.eedemos.provs.firma.entity.Firma;
import de.gedoplan.buch.eedemos.provs.firma.entity.Mitarbeiter;
import de.gedoplan.buch.eedemos.provs.firma.entity.Standort;
import de.gedoplan.buch.eedemos.provs.projekt.entity.Aufgabe;
import de.gedoplan.buch.eedemos.provs.projekt.entity.MitarbeiterAufgabe;
import de.gedoplan.buch.eedemos.provs.projekt.entity.Projekt;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

@Model
public class PlayGroundModel //implements Serializable
{
  @Inject
  MasterTestDataService masterTestDataService;

  @Inject
  Log                   logger;

  @Inject
  List<Firma>           firmen;

  @Inject
  List<Projekt>         projekte;

  @Transactional
  public void createTestFixture()
  {
    this.masterTestDataService.createTestFixture(null, null);
  }

  public List<Firma> getFirmen()
  {
    return this.firmen;
  }

  public TreeNode getFirmenTree()
  {
    TreeNode rootNode = new DefaultTreeNode("Root", null);
    TreeNode firmenNode = new DefaultTreeNode("Firmen", rootNode);
    for (Firma firma : getFirmen())
    {
      TreeNode firmaNode = new DefaultTreeNode(firma.toString(), firmenNode);
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

    return rootNode;
  }

  public List<Projekt> getProjekte()
  {
    return this.projekte;
  }

  public TreeNode getProjekteTree()
  {
    TreeNode rootNode = new DefaultTreeNode("Root", null);
    TreeNode projekteNode = new DefaultTreeNode("Projekte", rootNode);
    for (Projekt projekt : getProjekte())
    {
      addAufgabe(projekteNode, projekt);
    }

    return rootNode;
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
}
