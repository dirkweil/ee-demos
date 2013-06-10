package de.gedoplan.buch.eedemos.jsf.service;

import de.gedoplan.buch.eedemos.jsf.entity.Waehrung;
import de.gedoplan.buch.eedemos.jsf.repository.WaehrungRepository;

import java.io.Serializable;

import javax.inject.Inject;

public class WaehrungService implements Serializable
{
  @Inject
  private WaehrungRepository waehrungRepository;

  public double getTauschkurs(String waehrungsKuerzel)
  {
    Waehrung waehrung = this.waehrungRepository.findById(waehrungsKuerzel);
    if (waehrung != null)
    {
      return waehrung.getEuroValue();
    }

    throw new IllegalArgumentException("Waehrung " + waehrungsKuerzel + " unbekannt");
  }

  public double umrechnen(double waehrungsBetrag, String waehrungsKuerzel)
  {
    return waehrungsBetrag * getTauschkurs(waehrungsKuerzel);
  }
}
