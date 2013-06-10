package de.gedoplan.baselibs.persistence.embeddable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Gepackte String-Liste. Objekte dieses Typs enthalten eine Liste von Strings. Sie können in JPA-Entities eingebettet werden und
 * legen die Stringliste dann in Form eines gepackten Strings ab, der die Einzelwerte durch ein FS-Zeichen (0x1c) getrennt
 * enthält. Die Einzelstrings sollten selbst kein FS enthalten. Ist dies doch der Fall, wird es durch ein Blank ersetzt.
 * 
 * @author dw
 */
@Embeddable
@Access(AccessType.FIELD)
@Deprecated
public class PackedStringList implements Iterable<String>
{
  private static final String DELIM = "\u001c"; // ASCII-FS (Field Separator)

  /**
   * Gepackter Wert.
   * 
   * (Package private wegen Unit Test)
   */
  @Column(name = "VALUES")
  /* package_private */String values;

  /**
   * Werte liefern.
   * 
   * @return Liste der enthaltenen Strings
   */
  public List<String> getValues()
  {
    if (this.values == null)
    {
      return Collections.emptyList();
    }

    List<String> valueList = new ArrayList<String>();
    for (String s : this.values.split(DELIM))
    {
      valueList.add(s);
    }

    return Collections.unmodifiableList(valueList);
  }

  /**
   * Werte setzen.
   * 
   * @param valueList Liste der enthaltenen Strings
   */
  public void setValues(List<String> valueList)
  {
    if (valueList == null || valueList.isEmpty())
    {
      this.values = null;
    }

    StringBuilder buf = new StringBuilder();
    for (String s : valueList)
    {
      if (buf.length() != 0)
      {
        buf.append(DELIM);
      }

      buf.append(s.replace(DELIM, " "));
    }
    this.values = buf.toString();
  }

  /**
   * Wert anfügen.
   * 
   * @param value Wert
   */
  public void addValue(String value)
  {
    if (this.values == null)
    {
      this.values = value;
    }
    else
    {
      this.values += (DELIM + value);
    }
  }

  /**
   * Werte löschen.
   */
  public void clear()
  {
    this.values = null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return this.values != null ? this.values.replace(DELIM, "|") : "";
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Iterable#iterator()
   */
  @Override
  public Iterator<String> iterator()
  {
    return this.getValues().iterator();
  }
}
