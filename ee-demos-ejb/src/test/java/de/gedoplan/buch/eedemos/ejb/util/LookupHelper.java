package de.gedoplan.buch.eedemos.ejb.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class LookupHelper
{
  private static boolean initialized = false;

  private static String  moduleName;

  private static String  serverType;

  private static String  serverVersion;

  private static String  applicationName;

  public static String getEjbLookupName(Class<?> remoteClass, boolean stateful)
  {
    return getEjbLookupName(remoteClass, remoteClass.getSimpleName() + "Bean", stateful);
  }

  public static String getEjbLookupName(Class<?> remoteClass, String beanName, boolean stateful)
  {
    if (!initialized)
    {
      initialize();
    }

    if ("jboss".equalsIgnoreCase(serverType))
    {
      if ("7.1".equals(serverVersion))
      {
        return getJBoss7EjbLookupName(remoteClass, beanName, stateful);
      }

      return getJBoss6EjbLookupName(remoteClass, beanName);
    }

    return getGlobalEjbLookupName(remoteClass, beanName);
  }

  private static String getGlobalEjbLookupName(Class<?> remoteClass, String beanName)
  {
    StringBuilder lookUpName = new StringBuilder("java:global/");

    if (applicationName != null)
    {
      lookUpName.append(applicationName);
      lookUpName.append("/");
    }

    if (moduleName != null)
    {
      lookUpName.append(moduleName);
      lookUpName.append("/");
    }

    lookUpName.append(beanName);

    lookUpName.append("!");
    lookUpName.append(remoteClass.getName());

    return lookUpName.toString();
  }

  private static String getJBoss6EjbLookupName(Class<?> remoteClass, String beanName)
  {
    StringBuilder lookupName = new StringBuilder();
    if (applicationName != null)
    {
      lookupName.append(applicationName);
      lookupName.append('/');
    }
    lookupName.append(beanName);
    lookupName.append("/remote-");
    lookupName.append(remoteClass.getName());

    return lookupName.toString();
  }

  private static String getJBoss7EjbLookupName(Class<?> remoteClass, String beanName, boolean stateful)
  {
    StringBuilder lookUpName = new StringBuilder("ejb:");

    if (applicationName != null)
    {
      lookUpName.append(applicationName);
    }
    lookUpName.append("/");

    if (moduleName != null)
    {
      lookUpName.append(moduleName);
    }
    lookUpName.append("/");

    lookUpName.append(beanName);

    lookUpName.append("!");
    lookUpName.append(remoteClass.getName());

    if (stateful)
    {
      lookUpName.append("?stateful");
    }

    return lookUpName.toString();
  }

  private static void initialize()
  {
    InputStream inputStream = null;
    try
    {
      inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties");
      InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
      Properties prop = new Properties();
      prop.load(reader);

      applicationName = prop.getProperty("application.name");
      moduleName = prop.getProperty("module.name");
      serverType = prop.getProperty("server.type");
      serverVersion = prop.getProperty("server.version");

      initialized = true;
    }
    catch (Exception exception) // CHECKSTYLE:IGNORE
    {
      throw new RuntimeException("Kann Applikationsproperties nicht lesen", exception);
    }
    finally
    {
      try
      {
        inputStream.close();
      }
      catch (IOException ignore)
      {
      }
    }
  }

}
