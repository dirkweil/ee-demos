package de.gedoplan.baselibs.persistence.repository;

/**
 * Query-Filter.
 * 
 * @author dw
 */
public class QueryFilter
{
  /**
   * Pfadangabe für das Feld, auf das die Filterbedingung angewendet wird.
   * 
   * Hier sind ein oder mehrere Attributnamen enthalten; Trennzeichen ist '.'.
   */
  private String          pathExpression;

  /**
   * Vergleichswert.
   */
  private Object          value;

  /**
   * Filteroperation.
   */
  private FilterOperation filterOperation;

  /**
   * Konstruktor.
   * 
   * @param pathExpression Wert für {@link #pathExpression}
   * @param value Wert für {@link #value}
   * @param filterOperation Wert für {@link #filterOperation}
   */
  public QueryFilter(String pathExpression, Object value, FilterOperation filterOperation)
  {
    this.pathExpression = pathExpression;
    this.value = value;
    this.filterOperation = filterOperation;
  }

  /**
   * Wert liefern: {@link #pathExpression}.
   * 
   * @return Wert
   */
  public String getPathExpression()
  {
    return this.pathExpression;
  }

  /**
   * Wert liefern: {@link #value}.
   * 
   * @return Wert
   */
  public Object getValue()
  {
    return this.value;
  }

  /**
   * Wert liefern: {@link #filterOperation}.
   * 
   * @return Wert
   */
  public FilterOperation getFilterOperation()
  {
    return this.filterOperation;
  }

  /**
   * Filteroperation.
   * 
   * @author dw
   */
  public static enum FilterOperation
  {
    /**
     * Gleich.
     */
    EQUAL,

    /**
     * Präfix-Match.
     */
    PREFIX,

    /**
     * Enthält
     */
    CONTAINS;
  }
}
