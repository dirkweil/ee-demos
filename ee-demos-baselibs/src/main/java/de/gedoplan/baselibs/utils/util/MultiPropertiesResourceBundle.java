package de.gedoplan.baselibs.utils.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * ResourceBundle-Implementierung ähnlich zu PropertyResourceBundle, allerdings auf Basis aller passenden Properties-Dateien im Classpath.
 * 
 * Die Dateien werden in der Classpath-Reihenfolge verwendet, d. h. dass ein Eintrag in einem im Classpath früher liegenden Properties-File einen später vorhandenen Eintrag mit gleichem Schlüssel
 * überdeckt.
 * 
 * @author dw
 */
public class MultiPropertiesResourceBundle extends ResourceBundle
{
  private Map<String, String> messageMap = new HashMap<String, String>();

  /**
   * Konstruktor.
   * 
   * Entspricht {@link #MultiPropertiesResourceBundle(String)}, wobei als Name der voll qualifizierte Klassenname verwendet wird, in dem '.' durch '/' ersetzt wird.
   */
  public MultiPropertiesResourceBundle()
  {
    loadProperties(this.getClass().getName().replace('.', '/') + ".properties");
  }

  /**
   * Konstruktor.
   * 
   * Die Meldungen werden aus allen Properties-Dateien im Classpath gelesen, deren Name <code>bundleName + ".properties"</code> ist.
   * 
   * @param bundleName Name des Resource Bundle
   */
  public MultiPropertiesResourceBundle(String bundleName)
  {
    loadProperties(bundleName + ".properties");
  }

  private void loadProperties(String resourceName)
  {
    try
    {
      Enumeration<URL> found = Thread.currentThread().getContextClassLoader().getResources(resourceName);
      while (found.hasMoreElements())
      {
        URL url = found.nextElement();
        loadMessages(url);
      }
    }
    catch (IOException e)
    {
      // ignore
    }
  }

  private void loadMessages(URL url)
  {
    Reader reader = null;
    try
    {
      reader = new InputStreamReader(url.openStream(), "UTF-8");

      Properties prop = new Properties();
      prop.load(reader);

      for (Entry<Object, Object> entry : prop.entrySet())
      {
        String key = (String) entry.getKey();
        if (!messageMap.containsKey(key))
        {
          String value = (String) entry.getValue();
          messageMap.put(key, value);
        }
      }
    }
    catch (IOException e)
    {
      // ignore
    }
    finally
    {
      try
      {
        reader.close();
      }
      catch (Throwable e)
      {
        // ignore
      }
    }
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.util.ResourceBundle#getKeys()
   */
  @Override
  public Enumeration<String> getKeys()
  {
    Set<String> keys;
    if (parent == null)
    {
      keys = messageMap.keySet();
    }
    else
    {
      keys = new HashSet<String>(messageMap.keySet());
      keys.addAll(parent.keySet());
    }

    return Collections.enumeration(keys);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.util.ResourceBundle#handleKeySet()
   */
  @Override
  protected Set<String> handleKeySet()
  {
    return messageMap.keySet();
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.util.ResourceBundle#handleGetObject(java.lang.String)
   */
  @Override
  protected Object handleGetObject(String key)
  {
    return messageMap.get(key);
  }

}
