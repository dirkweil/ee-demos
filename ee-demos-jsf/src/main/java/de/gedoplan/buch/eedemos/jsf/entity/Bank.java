package de.gedoplan.buch.eedemos.jsf.entity;

import java.io.Serializable;

public class Bank implements Comparable<Bank>, Serializable
{
  private String blz;
  private String name;
  private String plz;
  private String ort;

  public Bank()
  {
  }

  public Bank(String blz, String name, String plz, String ort)
  {
    this.blz = blz;
    this.name = name;
    this.plz = plz;
    this.ort = ort;
  }

  public Bank(Bank other)
  {
    set(other);
  }

  public void set(Bank other)
  {
    this.blz = other.blz;
    this.name = other.name;
    this.plz = other.plz;
    this.ort = other.ort;
  }

  public String getBlz()
  {
    return this.blz;
  }

  public void setBlz(String blz)
  {
    this.blz = blz;
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getOrt()
  {
    return this.ort;
  }

  public void setOrt(String ort)
  {
    this.ort = ort;
  }

  public String getPlz()
  {
    return this.plz;
  }

  public void setPlz(String plz)
  {
    this.plz = plz;
  }

  @Override
  public boolean equals(Object other)
  {
    if (!(other instanceof Bank))
    {
      return false;
    }
    Bank otherBank = (Bank) other;
    return this.blz.equals(otherBank.blz) && this.plz.equals(otherBank.plz);
  }

  @Override
  public int hashCode()
  {
    return this.blz.hashCode() + this.plz.hashCode();
  }

  @Override
  public String toString()
  {
    return "Bank{" + this.blz + "," + this.name + "," + this.plz + "," + this.ort + "}";
  }

  @Override
  public int compareTo(Bank other)
  {
    int result = this.blz.compareTo(other.blz);
    if (result == 0)
    {
      result = this.plz.compareTo(other.plz);
    }
    return result;
  }
}
