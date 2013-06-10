package de.gedoplan.baselibs.utils.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Generischer N-ärer Baum.
 * 
 * @author dw
 * 
 * @param <T> Typ der Nutzdaten
 */
public class Tree<T>
{
  /**
   * Nutzdaten.
   */
  private T                     data;

  private List<Tree<T>> children = new ArrayList<>();

  /**
   * Konstruktor.
   * 
   * @param data Wert für {@link #data}
   */
  public Tree(T data)
  {
    this.data = data;
  }

  /**
   * Wert liefern: {@link #data}.
   * 
   * @return Wert
   */
  public T getData()
  {
    return this.data;
  }

  /**
   * Wert setzen: {@link #data}.
   * 
   * @param data Wert
   */
  public void setData(T data)
  {
    this.data = data;
  }

  /**
   * Wert liefern: {@link #children}.
   * 
   * @return Wert
   */
  public List<Tree<T>> getChildren()
  {
    return this.children;
  }
}
