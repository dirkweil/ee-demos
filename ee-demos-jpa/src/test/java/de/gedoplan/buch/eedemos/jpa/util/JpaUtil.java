package de.gedoplan.buch.eedemos.jpa.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaUtil
{
  private static final String         PROVIDER = "eclipselink";
  // private static final String PROVIDER = "hibernate";
  // private static final String PROVIDER = "openjpa";

  /*
   * Achtung: OpenJPA benötigt ein Enhancement der Klassen. Das kann zur Laufzeit mit einem Agenten geschehen. Dazu muss der VM
   * der folgende Parameter mitgegeben werden (Pfad zum Jar-File bitte anpassen):
   * -javaagent:C:\Users\dw\.m2\repository\org\apache\openjpa\openjpa-all\2.1.0\openjpa-all-2.1.0.jar=scanDevPath=true
   */

  private static EntityManagerFactory entityManagerFactory;

  public static synchronized EntityManagerFactory getEntityManagerFactory()
  {
    if (entityManagerFactory == null)
    {
      entityManagerFactory = Persistence.createEntityManagerFactory(PROVIDER);
    }

    return entityManagerFactory;
  }

  public static EntityManager createEntityManager()
  {
    return getEntityManagerFactory().createEntityManager();
  }

}
