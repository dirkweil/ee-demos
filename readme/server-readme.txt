Die Projekte können auf verschiedenen Java-EE-7-Servern deployt werden. 

Die Server selbst sind nicht Bestandteil der Projekte. Sie müssen vielmehr von den jeweiligen Download-Seiten geladen werden:
- GlassFish: http://glassfish.java.net/public/downloadsindex.html#top
- WildFly: http://www.wildfly.org/downloads/

Die Installation geschieht einfach durch Entpacken des Download-Files an einer Stelle Ihrer Wahl.


Das Deployment der Anwendungen geschieht durch Kopieren des Ergebnisses des Maven-Builds aus dem jeweiligen
target-Verzeichnis in das Autodeploy-Verzeichnis des Servers. Einige Beispiele (Server-Startverzeichnisse bitte anpassen):
- ee-demos\ee-demos-cdi\target\ee-demos-cdi.war --> glassfish-4.x\glassfish\domains\domain1\autodeploy
- ee-demos\ee-demos-jpa\target\ee-demos-jpa.war --> wildfly-8.x\standalone\deployments
Alternativ kann der gewünschte Server auch in die genutzte IDE integriert werden. Siehe dazu die Doku der IDE.


Zum Test der EJBs mit den Testprogrammen im Projekt ee-demos-ejb sind serverspezifische Bibliotheken und Verbindungsparameter
erforderlich. Diese werden durch Aktivierung des entsprechenden Profils im Maven-Build ausgewählt. Editieren Sie dazu die Datei pom.xml
im Projekt ee-demos-ejb. Die erwähnten Profile befinden sich am Ende der Datei. Setzen Sie im Element <activeByDefault>...</activeByDefault>
bei genau einem Server den Wert true ein.
Alternativ ist es bei Nutzung einer IDE i. d. R. möglich, darin das gewünschte Profil zu aktivieren.