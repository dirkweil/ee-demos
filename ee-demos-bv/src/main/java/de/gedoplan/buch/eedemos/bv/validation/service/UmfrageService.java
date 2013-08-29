package de.gedoplan.buch.eedemos.bv.validation.service;

import de.gedoplan.buch.eedemos.bv.validation.entity.Fragebogen;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class UmfrageService implements Serializable
{
  private static final long serialVersionUID = 1L;

  @NotNull
  public List<Fragebogen> createUmfrage(@Min(10) int personenZahl)
  {
    List<Fragebogen> umfrage = new ArrayList<>();
    for (int i = 0; i < personenZahl; ++i)
    {
      umfrage.add(new Fragebogen());
    }
    return umfrage;
  }

}
