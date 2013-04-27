Zur Demonstration von Security benötigen die Projekte User mit passenden Rechte, d. h. Rollen.
Die Namen der User sind natürlich frei wählbar. Im Folgenden werden diese verwendet:

Username Rollen
======== =============
kirk     provsUser
spock    provsUser
mccoy    provsUser
hugo     eeDemoUser,demoRole
otto     eeDemoUser

Die ersten drei User werden in Projekt ee-demos-provs genutzt, die beiden anderen in ee-demos-ejb und ee-demos-jsf.

Für den JBoss 7 ist die Einrichtung wie folgt durchzuführen:

- Im Verzeichnis bin der Serverinstallation befindet sich ein Skript namens add-user.bat bzw. add-user.sh, mit dem neue User angelegt werden können.
  Es wird einfach von der Kommandozeile aus gestartet und fragt die folgenden Informationen ab:
    Typ des Benutzers: Applikationsbenutzer
    Bereich:           ApplicationRealm
    Benutzername:      (s. oben)
    Passwort:          (frei wählbar; muss min. 8 Zeichen lang sein und Buchstaben, Ziffern und Sonderzeichen enthalten)
    Rollen:            (Kommagetrennte Liste von Rollennamen; s. oben)
