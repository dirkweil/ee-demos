package de.gedoplan.buch.eedemos.provs.common.util;

public class StringUtil
{
  public static String interpretEmptyStringAsNull(String s)
  {
    if (s == null || s.isEmpty())
    {
      return null;
    }

    return s;
  }
}
