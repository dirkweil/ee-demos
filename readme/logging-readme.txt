Die Projekte protokollieren teilweise ihre Aktionen ins Log des verwendeten Servers. Als Log-Kategorie wird - wie allgemein üblich - der
voll qualifierte Klassenname (de.gedoplan...) verwendet. Das Log-Level ist meist DEBUG, manchmal auch TRACE.

Die Server unterdrücken in ihrer Grundkonfiguration i. d. R. Log-Ausgaben der Levels unter INFO. Um die o. a. Ausgaben zu erhalten,
bitte je nach Applikationsserver wie folgt vorgehen:

a) GlassFish 3.1.x

   Die GlassFish-Logging-Konfiguration befindet sich in der Datei glassfish/domains/domain1/config/logging.properties. Hier kann mit einem
   Editor Ihrer Wahl die Zeile "de.gedoplan.level=ALL" ergänzt werden. Der Server muss anschließend neu gestartet werden.
   
b) JBoss 7.1.1

   Die Logging-Konfiguration kann mit der Administration Console (http://localhost:9990) vorgenommen werden. Hier bitte im Reiter "Profile" den 
   Menüpunkt "Core" -> "Logging" anwählen. Darin kann dann eine neue Log Category mit Namen "de.gedoplan" und Log Level "DEBUG" eingetragen 
   werden. Der vordefinierte Handler "CONSOLE" muss dann noch als Log Level bspw. "ALL" erhalten, damit die DEBUG-Meldungen auf der Console 
   sichtbar werden. Die Einstellungen werden im Betrieb sofort aktiv.
