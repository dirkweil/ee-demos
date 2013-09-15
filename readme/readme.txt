Allgemeines
===========

Die Projekte sind als Maven-Projekte aufgebaut. Das oberste Projekt "ee-demos" dient als Einstieg und zum Build aller Teilprojekte.

Maven selbst wie auch ein Java-SDK müssen vorweg installiert werden. Benötigt werden zumindest diese Versionen:
- Maven 3.0.3 (s. http://maven.apache.org/download.html)
- JDK 7 (s. http://www.oracle.com/technetwork/java/javase/downloads/index.html)

Definieren Sie bitte zwei Environment-Variablen, die die Installationsverzeichnisse von Maven und JDK enthalten:
  
  Variable   Wert
  ---------  --------------------------------------------------------------------------
  M2_HOME    Installations-Verzeichnis von Maven (z. B. D:\programme\apache-maven-3.0.5)
  JAVA_HOME  Installations-Verzeichnis des JDK (z. B. C:\Program Files\Java\jdk1.7.0_21)
  
Ergänzen Sie dann noch die Variable PATH um die jeweiligen bin-Verzeichnisse
  
  PATH       Windows: %M2_HOME%\bin;%JAVA_HOME%\bin;...
             Unix:    $M2_HOME/bin:$JAVA_HOME/bin:...
  
Ein "mvn" auf der Kommandozeile im obersten Projekt sollte ausreichen, um die Anwendungen zu bauen (im pom.xml ist als Default-Goal 
"install" eingetragen). Die Build-Ergebnisse finden sich dann Maven-typisch in den Ordnern "target" der Teilprojekte.

Bequemer ist zweifellos die Bearbeitung mit einer IDE. Hinweise dazu finden Sie im Ordner ide. Das Maß der Dinge ist aber der
Maven-Build. Nur wenn der problemlos durchführbar ist, sollten Sie die Projekte in eine IDE ihrer Wahl importieren.

Einige Projekte enthalten JUnit Tests (im jeweiligen Source-Ordner src/test). Für den normalen Build sind die Tests allerdings
ausgeschaltet. Zur Ausführung der Tests mit Maven muss daher ein Parameter zur Aktivierung der Test angegeben werden:
  mvn -DskipTests=false test

Einige Projekte erlauben die Nutzung unterschiedlicher Provider (JPA, App-Server, ...). Dazu sind entsprechende Maven-Profile
vorhanden. Sie werden auf der Kommandozeile wie folgt aktiviert (z. B. für die Nutzung von Hibernate im JPA-Projekt):
  mvn -Phibernate ...
Innerhalb von Eclipse kann das Profile gewählt werden, indem mit der rechten Maustaste auf das Projekt geklickt wird und dann im
Kontextmenü "Maven" -> "Select Maven Profiles" genutzt wird.   

Die folgenden Teilprojekte sind unterhalb von "ee-demos" vorhanden:

  Projekt         Inhalt                                               Nutzung
  --------------  ----------------------------------                   --------------------------------------------------------------------------------------------------
  ee-demos-cdi    Begleitprojekt zum Kapitel 2 "CDI"                   Auf den gewünschten Server deployen und per Browser aufrufen: http://localhost:8080/ee-demos-cdi/.
                                                                       Die Projekte ee-demos-cdi-... sind Unterprojekte von ee-demos-cdi und werden als Dependencies
                                                                       dort hinein paketiert.
                                                                       Zudem enthält das Projekt einige JUnit Tests im Ordner src/test/java.
                                                                       
  ee-demos-jpa    Begleitprojekt zum Kapitel 3 "JPA"                   Im Source-Ordner src/test/java sind einige Test-Programme enthalten, die direkt gestartet werden
                                                                       können (als JUnit Tests). Diese SE-Tests nutzen per Default EclipseLink als JPA-Provider. Mit dem
                                                                       Maven-Profil "hibernate" kann stattdessen Hibernate eingesetzt werden. 
                                                                       Zudem kann die Anwendung auf einen Server deployt werden. Im Browser sind unter
                                                                       http://localhost:8080/ee-demos-jpa zwei weitere Demos aufrufbar.
                                                                       
  ee-demos-bv     Begleitprojekt zum Kapitel 4 "Bean Validation"       Auf den gewünschten Server deployen und per Browser aufrufen: http://localhost:8080/ee-demos-bv/.
                                                                       Zusätzlich sind einige SE-Demos in src/test/java verfügbar.
                                                                       
  ee-demos-jsf    Begleitprojekt zum Kapitel 5 "JavaServer Faces"      Auf den gewünschten Server deployen und per Browser aufrufen: http://localhost:8080/ee-demos-jsf/.
                                                                       Das Projekt ee-demos-jsf-extflow ist ein Unterprojekt von ee-demos-jsf und wird als Dependency
                                                                       dort hinein paketiert.
                                                                       
  ee-demos-ejb    Begleitprojekt zum Kapitel 6 "Enterprise JavaBeans"  Auf den gewünschten Server deployen. Um die Tests unter src/test/java zu nutzen, muss das zum
                                                                       Server passende Maven-Profil aktiviert werden: glassfish bzw. wildfly.
                                                                       Zusätzlich zu dieses Tests sind auch im Browser unter http://localhost:8080/ee-demos-ejb weitere 
                                                                       Demos aufrufbar.
                                                                       
  ee-demos-provs  Übergreifendes Demoprojekt zu Kapitel 7              Auf den gewünschten Server deployen. Dann zunächst Testdaten erzeugen, indem per Browser der URL
                                                                       http://localhost:8080/ee-demos-provs/playground/createTestData.xhtml aufgerufen und der dortige
                                                                       Button "createTestFixture" genutzt wird. Anschließend kann die Anwendung unter 
                                                                       http://localhost:8080/ee-demos-provs/ aufgerufen werden
  



Server
======

Zur Server-Installation siehe server-readme.txt.

Die Projekte sind grundsätzlich unabhängig vom Ziel-Server. Getestet wurden Sie auf den folgenden Servern:

  Projekt         GlassFish 4.0.1 (B03)    WildFly 8.0.0 (Alpha 4)
  --------------  -----------------------  -----------------------
  ee-demos-cdi    ok                       ok
  
  ee-demos-jpa    [UnsyncEM]               [UnsynchEM]
  ee-demos-bv     [EmptyNull]              [EmptyNull]
  ee-demos-jsf    [ExtComp]                ok
  ee-demos-ejb    ?                        ?
  ee-demos-provs  ok                       ok
  
Einige Projekte enthalten auch JUnit Tests, wobei teilweise verschiedene Provider (CDI/JPA) eingesetzt werden können:
  
  Projekt         Weld 2.0.0.SP1
                  (Default)
  --------------  -----------------------
  ee-demos-cdi    ok
                  

  Projekt         EclipseLink 2.5.0        Hibernate 4.3.0.Beta3
                  (Default)                (Profil "hibernate")
  --------------  -----------------------  -----------------------
  ee-demos-jpa    [TreatComp]              [SqlCtor]
                  [DDLScript]
                  [EntityGraph]            [EntityGraph]

Folgende Bugs sind derzeit noch enthalten:

  [DDLScript]     Create und Drop Scripts werden nicht erstellt
  [EmptyNull]     Leere JSF-Eingabewerte des Typs String werden nicht als null geliefert
  [EntityGraph]   Load und Fetch Graphs werden nicht (vollständig) unterstützt
  [ExtComp]       Composite Components in externen JARs werden nicht gefunden
  [SqlCtor]       Native Query mit @ConstructorResult liefert Ergebnistyp Object[]
  [TreatComp]     Attribute mit TREAT umgetypter Objekte lassen sich nicht mit < oder > vergleichen
  [UnsyncEM]      @PersistenceContext(synchronization = SynchronizationType.UNSYNCHRONIZED) liefert TX-gebundenen EM


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


