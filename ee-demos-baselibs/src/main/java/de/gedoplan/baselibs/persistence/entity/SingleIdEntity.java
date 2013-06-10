/*
 * Copyright (c) 2011
 * GEDOPLAN Unternehmensberatung und EDV-Organisation GmbH
 * Stieghorster Str. 60, D-33605 Bielefeld, Germany
 * http://www.gedoplan.de
 * All rights reserved.
 */

package de.gedoplan.baselibs.persistence.entity;

import de.gedoplan.baselibs.utils.util.ClassUtil;
import de.gedoplan.baselibs.utils.util.MethodUtil;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 * Basisklasse für Entities mit einem einzelnen ID-Attribut.
 * 
 * @param <K> Typ des ID-Attributs
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class SingleIdEntity<K> implements Serializable
{
  @Transient
  private transient volatile SortedMap<String, Method> propertyMap = null;

  /**
   * Versionsattribut.
   */
  @SuppressWarnings("unused")
  @Version
  @Column(name = "UPD_COUNT")
  private long                                         updateCount;

  /**
   * Id liefern.
   * 
   * @return Id.
   */
  public abstract K getId();

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode()
  {
    K thisId = getId();
    return thisId != null ? thisId.hashCode() : 0;
  }

  /**
   * Vergleich dieses Objektes mit einem anderen.
   * 
   * Zwei Objekte vom Typ SingleKeyEntity werden als gleich eingestuft, wenn sie gleichen Typs sind und ihre Ids gesetzt und
   * gleich sind.
   * 
   * Achtung: Ist die Id mindestens eines der beiden Objekte <code>null</code>, werden die Objekte als 'ungleich' erachtet. Die
   * Annahme ist, dass die Id einer Entity nur dann <code>null</code> ist, wenn sie eine generierte Id besitzt und noch nicht
   * persistiert wurde. In diesem Fall wird wahrscheinlich kein Vergleich mit einem inhaltsgleichen Objekt durchgeführt, so dass
   * <code>false</code> als Ergebnis sinnvoll erscheint.
   * 
   * @param obj Vergleichsobjekt
   * @return <code>true</code> bei Gleichheit
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj)
  {
    if (this == obj)
    {
      return true;
    }
    if (obj == null)
    {
      return false;
    }
    if (getClass() != obj.getClass())
    {
      return false;
    }

    @SuppressWarnings("unchecked")
    final SingleIdEntity<K> other = (SingleIdEntity<K>) obj;

    K thisId = getId();
    K otherId = other.getId();

    return thisId != null && thisId.equals(otherId);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString()
  {
    return toDebugString(true);
  }

  /**
   * Objekt in String ausgeben in der Form type{id=...,...}.
   * 
   * Entspricht {@link #toDebugString(boolean) toDebugString(false)}.
   * 
   * @return Debug-String-Repräsentation des Objektes
   */
  public String toDebugString()
  {
    return toDebugString(false);
  }

  /**
   * Objekt in String ausgeben in der Form type{id=...,...}.
   * 
   * <p/>
   * Es wird ein String der Form <code>type{id=...,...}</code> erzeugt, wobei <code>type</code> der einfache Klassenname ist. Die
   * Property <code>id</code> wird als erstes angegeben. Ist <code>idOnly</code> <code>false</code>, folgen die weiteren
   * Properties des Objektes in alphabetisch sortierter Reihenfolge.
   * 
   * <p/>
   * Die Properties werden durch Aufsuchen der entsprechenden Getter-Methoden per Reflection ermittelt. Mit
   * <code>@Transient</code> markierte Methoden werden nicht berücksichtigt.
   * 
   * @param idOnly <code>true</code>, wenn nur <code>id</code> berücksichtigt werden soll
   * @return Debug-String-Repräsentation des Objektes
   */
  public String toDebugString(boolean idOnly)
  {
    if (this.propertyMap == null)
    {
      createPropertyMap();
    }

    StringBuilder buf = new StringBuilder(getClass().getSimpleName());
    buf.append("{id=");
    buf.append(getId());

    if (!idOnly)
    {
      for (Entry<String, Method> entry : this.propertyMap.entrySet())
      {
        String propertyName = entry.getKey();
        Method method = entry.getValue();

        buf.append(',');
        buf.append(propertyName);
        buf.append('=');
        try
        {
          buf.append(method.invoke(this, (Object[]) null));
        }
        catch (Exception ex) // CHECKSTYLE:IGNORE
        {
          buf.append(ex);
        }
      }
    }

    buf.append('}');

    return buf.toString();
  }

  private void createPropertyMap()
  {
    SortedMap<String, Method> p = new TreeMap<String, Method>();
    for (Method method : this.getClass().getMethods())
    {
      if (method.getParameterTypes().length == 0)
      {
        Class<?> returnType = method.getReturnType();
        if (isType4ToDebugString(returnType))
        {
          String propertyName = MethodUtil.getPropertyName(method.getName());

          if (propertyName != null && !"id".equals(propertyName) && method.getAnnotation(Transient.class) == null)
          {
            p.put(propertyName, method);
          }
        }
      }
    }

    this.propertyMap = p;
  }

  private boolean isType4ToDebugString(Class<?> clazz)
  {
    return clazz.isPrimitive() || clazz.isEnum() || ClassUtil.isPrimitiveWrapper(clazz) || ClassUtil.isTemporal(clazz) || clazz.equals(String.class);
  }

  // /**
  // * Konstruktor für interne Zwecke.
  // */
  // protected SingleIdEntity()
  // {
  // }
}
