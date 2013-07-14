Zur Demonstration von Security benötigen die Projekte User mit passenden Rechte, d. h. Rollen.
Die Namen der User sind natürlich frei wählbar. Im Folgenden werden diese verwendet:

Username  Rollen
========  =============
kirk      provsUser
spock     provsUser
mccoy     provsUser
hugo      eeDemoUser,demoRole
otto      eeDemoUser
anonymous guest

Die ersten drei User werden in Projekt ee-demos-provs genutzt, die nächsten beiden in ee-demos-ejb und ee-demos-jsf. Der User anonymous wird benötigt,
da JBoss 7 keine Remote-Aufrufe ohne Autentisierung erlaubt. Unsere Client-Bibliothek benutzt in einem solchen Fall den User anonymous.

Für den JBoss 7 ist die Einrichtung wie folgt durchzuführen:

- Im Verzeichnis bin der Serverinstallation befindet sich ein Skript namens add-user.bat bzw. add-user.sh, mit dem neue User angelegt werden können.
  Es wird einfach von der Kommandozeile aus gestartet und fragt die folgenden Informationen ab:
    Typ des Benutzers: Applikationsbenutzer
    Bereich:           ApplicationRealm
    Benutzername:      (s. oben)
    Passwort:          (frei wählbar*; muss min. 8 Zeichen lang sein und Buchstaben, Ziffern und Sonderzeichen enthalten)
    Rollen:            (Kommagetrennte Liste von Rollennamen; s. oben)
 
    *) mit Ausnahme des Users anonymous: Hier muss das Passwort anonymous_123 sein.
    