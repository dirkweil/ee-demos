package de.gedoplan.baselibs.utils.util;

/**
 * Utility-Klasse mit diversen Hilfsmethoden für Methoden.
 * 
 * @author dw
 */
public final class MethodUtil
{
  /**
   * Propertynamen ermitteln.
   * 
   * Der Property-Name wird entsprechend der JavaBeans-Konventionen aus dem Methodennamen abgeleitet. Entspricht der Methodenname
   * nicht der Konvention, wird <code>null</code> zurückgegeben.
   * 
   * @param methodName Name einer Getter- oder Settermethode
   * @return Name der entsprechenden Property oder <code>null</code>
   */
  public static String getPropertyName(String methodName)
  {
    String propertyName = null;

    if (methodName.length() >= 4 && (methodName.startsWith("get") || methodName.startsWith("set")))
    {
      propertyName = Character.toLowerCase(methodName.charAt(3)) + methodName.substring(4);
    }
    else if (methodName.length() >= 3 && methodName.startsWith("is"))
    {
      propertyName = Character.toLowerCase(methodName.charAt(2)) + methodName.substring(3);
    }

    return propertyName;
  }

  /**
   * Name der Getter-Methode ermitteln.
   * 
   * @param propertyName Property-Name
   * @return Name der Getter-Methode
   */
  public static String getGetterName(String propertyName)
  {
    return "get" + Character.toUpperCase(propertyName.charAt(0)) + propertyName.substring(1);
  }

  /**
   * Name der Setter-Methode ermitteln.
   * 
   * @param propertyName Property-Name
   * @return Name der Setter-Methode
   */
  public static String getSetterName(String propertyName)
  {
    return "set" + Character.toUpperCase(propertyName.charAt(0)) + propertyName.substring(1);
  }

  private MethodUtil()
  {
  }

}
