package de.gedoplan.baselibs.utils.util;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Utility-Klasse mit diversen Hilfsmethoden für Klassen.
 * 
 * @author dw
 */
public final class ClassUtil
{
  private static Set<Class<?>> primitiveWrappers = new HashSet<Class<?>>();
  static
  {
    primitiveWrappers.add(Byte.class);
    primitiveWrappers.add(Short.class);
    primitiveWrappers.add(Integer.class);
    primitiveWrappers.add(Long.class);
    primitiveWrappers.add(Float.class);
    primitiveWrappers.add(Double.class);
    primitiveWrappers.add(Character.class);
    primitiveWrappers.add(Boolean.class);
  }

  private static Set<Class<?>> temporals         = new HashSet<Class<?>>();
  static
  {
    temporals.add(Date.class);
    temporals.add(java.sql.Date.class);
    temporals.add(Timestamp.class);
    temporals.add(Time.class);
    temporals.add(Calendar.class);
  }

  /**
   * Ist die übergebene Klasse eine Wrapperklasse eines primitiven Typs?
   * 
   * @param clazz Klasse
   * @return <code>true</code>, wenn die Klasse eine Wrapper-Klasse ist
   */
  public static boolean isPrimitiveWrapper(Class<?> clazz)
  {
    return primitiveWrappers.contains(clazz);
  }

  /**
   * Ist die übergebene Klasse eine Datum/Uhrzeit-Klasse?
   * 
   * @param clazz Klasse
   * @return <code>true</code>, wenn die Klasse eine Datum/Uhrzeit-Klasse ist
   */
  public static boolean isTemporal(Class<?> clazz)
  {
    return temporals.contains(clazz);
  }

  private ClassUtil()
  {
  }

}
