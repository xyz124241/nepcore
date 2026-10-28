# Neptun – GitHub Build-Projekt

Dieses Repository enthält den NeptunCore-Quellcode, die `[i]`-Chat-Item-Funktion und die beiden gelieferten Zusatz-Plugins. GitHub Actions baut NeptunCore automatisch und stellt ein Server-Paket als Download bereit.

## Inhalt

- `src/` – Java-Quellcode von NeptunCore
- `src/main/java/de/neptun/core/ChatItemListener.java` – `[i]` im Chat: zeigt das Item aus der Haupthand mit Itemname/Farbe, Anzahl und Verzauberungen beim Darüberfahren an
- `bundled-plugins/NeptuneMessages-1.0.0.jar` – Original-Plugin für Nachrichten
- `bundled-plugins/NeptuneCommandWhitelist-1.0.0.jar` – Original-Plugin für die Command-Whitelist
- `.github/workflows/build.yml` – automatischer Build und Paket-Erstellung auf GitHub
- `INSTALLATION.md` – Installations- und Build-Anleitung

## Wichtig zur Zusammenführung

NeptunCore bündelt die NeptunCore-Module in einer JAR. Die beiden gelieferten Zusatz-Plugins bleiben im Server-Paket eigenständige JARs. Sie einfach in eine einzelne JAR zu kopieren, würde ihre Bukkit/Paper-Plugin-Startlogik nicht automatisch zusammenführen und könnte Befehle, Listener und Konfigurationen kaputtmachen. Eine echte Integration dieser beiden Plugins in NeptunCore benötigt deren Quellcode und eine Anpassung ihrer Plugin-Hauptklassen.

## Automatischer Build

Nach dem Push auf GitHub: `Actions` → `Build Neptun package` → den erfolgreichen Lauf öffnen → unter `Artifacts` `Neptun-Server-Paket` herunterladen. Die JARs liegen im ZIP unter `plugins/`.

Bei einem Tag wie `v1.0.1` wird zusätzlich ein GitHub Release mit der Core-JAR und dem Server-Paket veröffentlicht.

**Build-Status:** Die Workflow-Datei ist eingerichtet, aber der Build muss auf GitHub tatsächlich laufen. Erst ein grüner erfolgreicher Workflow bestätigt, dass sich der Quellcode mit den aktuellen Paper-Abhängigkeiten kompilieren lässt. Danach auf einer Testkopie des Servers testen, bevor du deine Live-JAR ersetzt.
