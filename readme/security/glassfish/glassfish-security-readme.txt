Zur Demonstration von Security benötigen die Projekte User mit passenden Rechten, d. h. Rollen.
Die Namen der User sind natürlich frei wählbar. Im Folgenden werden diese verwendet:

Username Rollen
======== =============
kirk     provsUser
spock    provsUser
mccoy    provsUser
hugo     eeDemoUser:demoRole
otto     eeDemoUser

Die ersten drei User werden in Projekt ee-demos-provs genutzt, die beiden anderen in ee-demos-ejb und ee-demos-jsf.

Die User können mit der Admin Console des GlassFish (http://localhost:4848) wie folgt angelegt werden:

In den Dialog "Configuration" -> "server-config" -> "Security" -> "Realms" -> "file" navigieren.
"Manage Users ..." klicken.
"Neu ..." klicken.
O. a. Werte eingeben und "Ok" klicken. Achtung: In der Group List ist das Trennzeichen ein Doppelpunkt!

GlassFish verwaltet User in Gruppen, während die Java-EE-Anwendungen Rollen benötigen. Wir verwenden
die Default-Zuordnung zwischen Principals (User, Gruppen) und Rollen. Diese wird wie folgt eingeschaltet:

In den Dialog "Configuration" -> "server-config" -> "Security" navigieren.
"Default Principal to Role Assignment" aktivieren und "Ok" klicken:

Achtung: Diese Einstellung wirkt sich nur auf Anwendungen aus, die nach der Änderung deployt werden.

Neben der Verwaltung der User in einer Datei bietet GlassFish auch andere Möglichkeiten für die Ablage von
Usern und Gruppen an. Dazu sei auf die GlassFish-Dokumentation verwiesen.