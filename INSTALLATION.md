# Bau- und Installationsplan für Neptun

## A. GitHub-Repository einrichten

1. Melde dich bei GitHub an und erstelle ein neues Repository, zum Beispiel `Neptun-GitHub-Projekt`.
2. Lade den gesamten Inhalt dieses Ordners in das Repository hoch. Die Ordnerstruktur muss erhalten bleiben, insbesondere `.github/workflows/build.yml`.
3. Öffne im Repository `Actions` und erlaube GitHub Actions, falls GitHub danach fragt.
4. Öffne `Actions` → `Build Neptun package` → `Run workflow`, oder pushe eine Änderung. Der Workflow verwendet Java 21 und Maven und baut `NeptunCore`.
5. Wenn der Lauf erfolgreich ist, lade das Artifact `Neptun-Server-Paket` herunter. Im ZIP befinden sich die drei Plugin-JARs im Ordner `plugins/`.

## B. Auf einem Testserver installieren

1. Erstelle ein vollständiges Backup des Servers und teste zunächst auf einer Kopie.
2. Stoppe den Server vollständig.
3. Sichere die bisherigen JARs und ihre Datenordner außerhalb von `plugins/`.
4. Lege `NeptunCore-1.0.0.jar`, `NeptuneMessages-1.0.0.jar` und `NeptuneCommandWhitelist-1.0.0.jar` aus dem Paket in `plugins/`.
5. Entferne alte Einzel-Plugins, die bereits von NeptunCore ersetzt werden (siehe Modul-Tabelle in `README.md`), damit Befehle und Listener nicht doppelt registriert werden. **Lass die beiden Zusatz-Plugins NeptuneMessages und NeptuneCommandWhitelist liegen**, sofern du sie weiter nutzen willst.
6. Starte den Server und prüfe die Konsole auf `ERROR`, `Could not load` oder fehlende Abhängigkeiten. Für die Command-Whitelist wird LuckPerms benötigt.
7. Teste `/neptun`, `/spawn`, `/home`, `/sell`, `/shop`, `/nm`, `/ncw` und `[i]` im Chat. Halte ein benanntes/verzaubertes Item in der Haupthand und schreibe zum Beispiel `Schaut mein Item [i]`.
8. Erst nach erfolgreichem Test auf den Live-Server übertragen.

## C. Lokal bauen (optional)

Voraussetzungen: JDK 21 und Maven 3.9+.

Im Projektordner ausführen:

```bash
mvn clean package
```

Die Core-JAR entsteht unter `target/NeptunCore-1.0.0.jar`.

## D. Release bauen

Nach einem erfolgreichen Build einen Git-Tag `v1.0.1` erstellen und pushen. GitHub Actions veröffentlicht dann ein Release mit Core-JAR und Server-Paket.

## Hinweise

- Der Workflow baut NeptunCore; die zwei bereitgestellten JARs werden unverändert ins Server-Paket kopiert.
- Das Paket enthält aktuell drei JARs und ist **kein einzelnes All-in-one-JAR**. Eine echte Integration der zwei Zusatz-Plugins braucht deren Quellcode.
- Der Build und die Laufzeit müssen durch den GitHub-Workflow und einen Testserver bestätigt werden. Ein Workflow-File allein ist keine Garantie, dass keine Fehler vorhanden sind.
