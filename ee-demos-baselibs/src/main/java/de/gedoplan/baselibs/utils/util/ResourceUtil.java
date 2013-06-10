package de.gedoplan.baselibs.utils.util;

import java.io.InputStream;
import java.net.URL;

/**
 * Helfer-Klasse zum Auffinden von Classpath-Resources.
 * 
 * @author dw
 */
public final class ResourceUtil
{
  /**
   * Classpath-Resource suchen.
   * 
   * @param name Name der Resource
   * @return URL der Resource oder <code>null</code>, wenn nicht gefunden
   */
  public static URL getResource(String name)
  {
    URL url = Thread.currentThread().getContextClassLoader().getResource(name);
    if (url == null)
    {
      url = ClassLoader.getSystemClassLoader().getResource(name);
    }
    if (url == null)
    {
      url = ResourceUtil.class.getResource(name);
    }
    return url;
  }

  /**
   * Classpath-Resource suchen und Stream darauf öffnen.
   * 
   * @param name Name der Resource
   * @return InputStream auf der Resource oder <code>null</code>, wenn nicht gefunden
   */
  public static InputStream getResourceAsStream(String name)
  {
    InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
    if (is == null)
    {
      is = ClassLoader.getSystemClassLoader().getResourceAsStream(name);
    }
    if (is == null)
    {
      is = ResourceUtil.class.getResourceAsStream(name);
    }
    return is;
  }

  private ResourceUtil()
  {
  }
}
