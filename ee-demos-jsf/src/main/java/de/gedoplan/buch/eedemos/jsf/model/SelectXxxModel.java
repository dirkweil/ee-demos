package de.gedoplan.buch.eedemos.jsf.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;

@Named
@SessionScoped
public class SelectXxxModel implements Serializable
{
  private boolean someBoolean = true;

  public boolean isSomeBoolean()
  {
    return this.someBoolean;
  }

  public void setSomeBoolean(boolean someBoolean)
  {
    this.someBoolean = someBoolean;
  }

  private int note = 2;

  public int getNote()
  {
    return this.note;
  }

  public void setNote(int month)
  {
    this.note = month;
  }

  public List<SelectItem> getNotenListe()
  {
    List<SelectItem> notenList = new ArrayList<>();
    notenList.add(new SelectItem(1, "sehr gut"));
    notenList.add(new SelectItem(2, "gut"));
    notenList.add(new SelectItem(3, "befriedigend"));
    notenList.add(new SelectItem(4, "unbefriedigend"));
    return notenList;
  }

  private List<String> eiskugeln;

  public List<String> getEiskugeln()
  {
    return this.eiskugeln;
  }

  public void setEiskugeln(List<String> eiskugeln)
  {
    this.eiskugeln = eiskugeln;
  }

  public String[] getEisKugelListe()
  {
    return new String[] { "Vanille", "Schoko", "Erdbeer" };
  }

  private Ampel ampel = Ampel.ROT; ;

  /**
   * Wert liefern: {@link #ampel}.
   * 
   * @return Wert
   */
  public Ampel getAmpel()
  {
    return this.ampel;
  }

  /**
   * Wert setzen: {@link #ampel}.
   * 
   * @param ampel Wert
   */
  public void setAmpel(Ampel ampel)
  {
    this.ampel = ampel;
  }

  public Ampel[] getAmpelwerte()
  {
    return Ampel.values();
  }

}
