package de.gedoplan.buch.eedemos.jsf.model;

import de.gedoplan.buch.eedemos.jsf.entity.Waehrung;
import de.gedoplan.buch.eedemos.jsf.repository.WaehrungRepository;
import de.gedoplan.buch.eedemos.jsf.service.WaehrungService;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class WaehrungsRechnerModel implements Serializable
{
  @Inject
  private WaehrungService    waehrungService;

  @Inject
  private WaehrungRepository waehrungRepository;

  private String             fremdWaehrungsKuerzel = "USD";
  private double             fremdWaehrungsBetrag;
  private double             euroBetrag;

  public String getFremdWaehrungsKuerzel()
  {
    return this.fremdWaehrungsKuerzel;
  }

  public void setFremdWaehrungsKuerzel(String fremdWaehrungsKuerzel)
  {
    this.fremdWaehrungsKuerzel = fremdWaehrungsKuerzel;
  }

  public double getFremdWaehrungsBetrag()
  {
    return this.fremdWaehrungsBetrag;
  }

  public void setFremdWaehrungsBetrag(double fremdWaehrungsBetrag)
  {
    this.fremdWaehrungsBetrag = fremdWaehrungsBetrag;
  }

  public double getEuroBetrag()
  {
    return this.euroBetrag;
  }

  public void umrechnen()
  {
    // Falls negativer Betrah eingegeben, auf Hilfeseite navigieren
    // Anm.: Das wäre eleganter möglich gewesen: return "help";
    if (this.fremdWaehrungsBetrag < 0)
    {
      FacesContext facesContext = FacesContext.getCurrentInstance();
      NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
      navigationHandler.handleNavigation(facesContext, null, "help");
    }

    this.euroBetrag = this.waehrungService.umrechnen(this.fremdWaehrungsBetrag, this.fremdWaehrungsKuerzel);
  }

  public List<Waehrung> getWaehrungen()
  {
    return this.waehrungRepository.findAll();
  }
}
