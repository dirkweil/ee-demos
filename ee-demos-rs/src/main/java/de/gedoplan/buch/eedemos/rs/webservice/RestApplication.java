package de.gedoplan.buch.eedemos.rs.webservice;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Einstiegspunkt für alle REST-Zugriffe.
 *
 * Durch die übernahme der Default-Implementierung {@link Application} "sammelt" diese Klasse alle REST-Ressourcen der Anwendung
 * ein, d. h. diese sind alle über den angegebenen Pfad erreichbar.
 *
 * @author dw
 */
@ApplicationPath("rest")
public class RestApplication extends Application
{
  /*
   * Ohne Ueberschreiben der Methode getClasses werden alle mit @Path annotierten Klassen einbezogen.
   * Wird die Methode implementiert, sind nur die explizit aufgeführten Klassen RESTful Services.
   */
  // public Set<Class<?>> getClasses()
  // {
  // Set<Class<?>> classes = new HashSet<>();
  // classes.add(HelloWorldResource.class);
  // return classes;
  // }
}
