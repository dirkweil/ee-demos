Die Projekte können auf verschiedenen Java-EE-6-Servern deployt werden. 

Die Server selbst sind nicht Bestandteil der Projekte. Sie müssen vielmehr von den jeweiligen Download-Seiten geladen werden:
- GlassFish: http://glassfish.java.net/public/downloadsindex.html#top
- JBoss: http://www.jboss.org/jbossas/downloads/

Die Installation geschieht einfach durch Entpacken des Download-Files an einer Stelle Ihrer Wahl.


Das Deployment der Anwendungen geschieht durch Kopieren des Ergebnisses des Maven-Builds aus dem jeweiligen
target-Verzeichnis in das Autodeploy-Verzeichnis des Servers. Einige Beispiele (Server-Startverzeichnisse bitte anpassen):
- ee-demos\ee-demos-cdi\target\ee-demos-cdi.war --> glassfish-3.1.2.2\glassfish\domains\domain1\autodeploy
- ee-demos\ee-demos-jsf\target\ee-demos-jsf.war --> jboss-6.1.0.Final\server\default\deploy
- ee-demos\ee-demos-jpa\target\ee-demos-jpa.war --> jboss-as-7.1.1.Final\standalone\deployments


Zum Test der EJBs mit den Testprogrammen im Projekt ee-demos-ejb sind serverspezifische Bibliotheken und Verbindungsparameter
erforderlich. Diese werden durch Aktivierung des entsprechenden Profils im Maven-Build ausgewählt. Editieren Sie dazu die Datei pom.xml
im Oberprojekt ee-demos. Die erwähnten Profile befinden sich am Ende der Datei. Setzen Sie im Element <activeByDefault>...</activeByDefault>
bei genau einem Server den Wert true ein.