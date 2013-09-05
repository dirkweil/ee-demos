Allgemeines
===========

Die Projekte sind als Maven-Projekte aufgebaut. Das oberste Projekt "ee-demos" dient als Einstieg und zum Build aller Teilprojekte.

Maven selbst wie auch ein Java-SDK müssen vorweg installiert werden. Benötigt werden zumindest diese Versionen:
- Maven 3.0.3 (s. http://maven.apache.org/download.html)
- JDK 7 (s. http://www.oracle.com/technetwork/java/javase/downloads/index.html)

Ein "mvn" auf der Kommandozeile im obersten Projekt sollte ausreichen, um die Anwendungen zu bauen (im pom.xml ist als Default-Goal 
"install" eingetragen).
Die Build-Ergebnisse finden sich dann Maven-typisch in den Ordnern "target" der Teilprojekte. 

Einfacher ist zweifellos die Bearbeitung mit einer IDE. Wenn Sie Eclipse nutzen, benötigen Sie dazu ein Maven-Plugin.
Ich habe gute Erfahrungen mit der Kombination Eclipse 3.7 (Indigo) + JBoss Tools gemacht. Leider war es einige Zeit lang so, dass 
die aus dem Eclipse-Marketplace installierbare Version der JBoss Tools nicht passend war. Eine Alternative, die das ausschließt, 
ist das JBoss Developer Studio (https://devstudio.jboss.com). Anders als der Name es vermuten lässt, ist es nicht nur für den JBoss 
Application Server nutzbar. Diese beiden Varianten erlauben den Import des Projektes als Maven-Projekt:
File -> Import -> Maven -> Existing Maven Projects. Als Root Directory dann das Startverzeichnis der Projekte auswählen. Ggf. wird 
während des Imports noch das eine oder andere Plugin in Eclipse installiert; dieser Prozess sollte aber einigermaßen selbsterklärend 
sein. Eclipse enthält diverse Validatoren, die die Korrektheit von Deskriptoren etc. testen sollen. Leider funktionieren diese
Validatoren (zumindest für Maven-Projekte) nicht korrekt. Wenn nach dem Import der Beispielprojekte Validierungsfehler (z. B. "JPA Problem")
angezeigt werden, sollten Sie die Validatoren deaktiviern (Window -> Preferences -> Validation: Disable All).

Die folgenden Teilprojekte sind unterhalb von "ee-demos" vorhanden:

  Projekt         Inhalt                                               Nutzung
  --------------  ----------------------------------                   --------------------------------------------------------------------------------------------------
  ee-demos-cdi    Begleitprojekt zum Kapitel 2 "CDI"                   Auf den gewünschten Server deployen und per Browser aufrufen: http://localhost:8080/ee-demos-cdi/.
  ee-demos-jpa    Begleitprojekt zum Kapitel 3 "JPA"                   Im Source-Ordner src/test/java sind einige Main-Programme enthalten, die direkt gestartet werden
                                                                       können. Zudem kann die Anwendung auf einen Server deployt werden. Im Browser sind unter
                                                                       http://localhost:8080/ee-demos-jpa zwei weitere Demos aufrufbar.
  ee-demos-bv     Begleitprojekt zum Kapitel 4 "Bean Validation"       Auf den gewünschten Server deployen und per Browser aufrufen: http://localhost:8080/ee-demos-bv/.
                                                                       Zusätzlich sind einige SE-Demos in src/test/java verfügbar.
  ee-demos-jsf    Begleitprojekt zum Kapitel 5 "JavaServer Faces"      Auf den gewünschten Server deployen und per Browser aufrufen: http://localhost:8080/ee-demos-jsf/.
  ee-demos-ejb    Begleitprojekt zum Kapitel 6 "Enterprise JavaBeans"  Auf den gewünschten Server deployen. Um die Tests unter src/test/java zu nutzen, muss das zum
                                                                       Server passende Maven-Profil aktiviert werden: glassfish-3.1.x bzw. jboss-7.1.x. Das kann bspw.
                                                                       in Eclipse geschehen, indem mit der rechten Maustaste auf das Projekt geklickt wird und dann im
                                                                       Kontextmenü "Maven" -> "Select Maven Profiles" genutzt wird. Zusätzlich zu dieses Tests sind auch
                                                                       im Browser unter http://localhost:8080/ee-demos-ejb weitere Demos aufrufbar.
  ee-demos-provs  Übergreifendes Demoprojekt zu Kapitel 7              Auf den gewünschten Server deployen. Dann zunächst Testdaten erzeugen, indem per Browser der URL
                                                                       http://localhost:8080/ee-demos-provs/playground/createTestData.xhtml aufgerufen und der dortige
                                                                       Button "createTestFixture" genutzt wird. Anschließend kann die Anwendung unter 
                                                                       http://localhost:8080/ee-demos-provs/ aufgerufen werden
  



Server
======

Die Projekte sind grundsätzlich unabhängig vom Ziel-Server. Getestet wurden Sie auf GlassFish 4.0.x und WildFly 8.0.0.
Siehe server-readme.txt.


Datenbank
=========

Die Projekte benötigen eine Datenbank.

In den SE-Anteilen der Projekte ist der Zugriff auf eine H2-Datenbank mit den folgenden Verbindungsparametern konfiguriert:
  Connect-URL: jdbc:h2:~/h2/ee-demos;AUTO_SERVER=TRUE
  User: ee-demos
  Passwort: ee-demos
  
Im verwendeten Applikationsserver muss eine Datasource mit dem JNDI-Namen jdbc/eeDemos konfiguriert werden, die auf die gleiche Datenbank verweist.
Dazu bitte je nach Applikationsserver wie in database/xxx/xxx-database-readme.txt angegeben vorgehen.

Zum direkten Betrachten und Bearbeiten der DB-Inhalte eignen sich diverse JDBC-Tools, z. B. 
  DbVisualizer:    http://www.dbvis.com        (Achtung: Versionen vor 8.x unterstützen Java 7 nicht!)
  SQuirrel SQL:    http://www.squirrelsql.org  (Base-Version reicht)
  SQL Workbench/J: http://www.sql-workbench.net


Security
========

Zur Demonstration von Security benötigen die Projekte User mit passenden Rechte, d. h. Rollen.
Die Namen der User sind natürlich frei wählbar. Im Folgenden werden diese verwendet:

Username Rollen
======== =============
kirk     provsUser
spock    provsUser
mccoy    provsUser
hugo     eeDemoUser,demoRole
otto     eeDemoUser

Die ersten drei User werden im Projekt ee-demos-provs genutzt, die beiden anderen in ee-demos-ejb und ee-demos-jsf.
Die Anleitungen in security/xxx/xxx-security-readme.txt zeigen die Konfiguration für den gewählten Server xxx.


