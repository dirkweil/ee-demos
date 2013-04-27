package de.gedoplan.buch.eedemos.provs.projekt.util;

import de.gedoplan.buch.eedemos.provs.projekt.entity.Aufgabe;
import de.gedoplan.buch.eedemos.provs.projekt.entity.MitarbeiterAufgabe;
import de.gedoplan.buch.eedemos.provs.projekt.entity.ProjektZeit;

import java.util.ArrayList;
import java.util.List;

// CHECKSTYLE:OFF

public class AufgabeMitarbeiteraufgabeProjektzeitHolder
{
  private Aufgabe            aufgabe;
  private MitarbeiterAufgabe mitarbeiterAufgabe;
  private List<ProjektZeit>  projektZeiten;

  public AufgabeMitarbeiteraufgabeProjektzeitHolder(Aufgabe aufgabe, MitarbeiterAufgabe mitarbeiterAufgabe)
  {
    this.aufgabe = aufgabe;
    this.mitarbeiterAufgabe = mitarbeiterAufgabe;
    this.projektZeiten = new ArrayList<>();
  }

  public Aufgabe getAufgabe()
  {
    return this.aufgabe;
  }

  public void setAufgabe(Aufgabe aufgabe)
  {
    this.aufgabe = aufgabe;
  }

  public MitarbeiterAufgabe getMitarbeiterAufgabe()
  {
    return this.mitarbeiterAufgabe;
  }

  public void setMitarbeiterAufgabe(MitarbeiterAufgabe mitarbeiterAufgabe)
  {
    this.mitarbeiterAufgabe = mitarbeiterAufgabe;
  }

  public List<ProjektZeit> getProjektZeiten()
  {
    return this.projektZeiten;
  }
}
