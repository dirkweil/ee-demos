package de.gedoplan.buch.eedemos.jsf.repository;

import de.gedoplan.buch.eedemos.jsf.entity.Waehrung;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WaehrungRepository implements Serializable
{
  private Map<String, Waehrung> waehrungMap;

  public Waehrung findById(String id)
  {
    return this.waehrungMap.get(id);
  }

  public List<Waehrung> findAll()
  {
    return new ArrayList<>(this.waehrungMap.values());
  }

  @SuppressWarnings("unused")
  @PostConstruct
  private void init()
  {
    this.waehrungMap = new TreeMap<>();
    initWaehrung(new Waehrung("CHF", 0.8278));
    initWaehrung(new Waehrung("GBP", 1.1967));
    initWaehrung(new Waehrung("USD", 0.7745));
  }

  private void initWaehrung(Waehrung waehrung)
  {
    this.waehrungMap.put(waehrung.getId(), waehrung);
  }
}
