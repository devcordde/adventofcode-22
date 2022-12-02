# adventofcode-22

Ein Sammel-Repository für Advent of Code 2022-Lösungen von Mitgliedern der deutschen [DevCord](https://discord.gg/tNMq2K4)-Discord-Community.

A repository collecting Advent of Code 2022 solutions made by members of the German [DevCord](https://discord.gg/tNMq2K4) Discord community.

## Lösungen / Solutions

Hier ist eine Liste mit allen von Beitragenden verwendeten Programmiersprachen und welche Tage mit diesen gelöst wurden:

Here is a list of all programming languages used by the contributors and the days that were solved with them:

| Day/Lang                              | Java | Python | Kotlin | Ruby | Elixir | JavaScript    | Perl    | C    | Clojure    | Haskell    | Nim    | C#    | Scala    | C++    | Go    | Groovy   | Rust   |
| ------ | ------ | ------ | ------ | ------ | ------ | ------ | ------ | ------ | ------ | ------ | ------ | ------ | ------ | ------ | ------ | ------ | ------ |
| 01 - Calorie Counting                 | ✅     | ✅     | ✅    | ✅      | ✅      | ✅      | ✅      | ✅      | ✅      | ✅      | ✅      | ✅      | ✅      | ✅      | ✅      | ✅      | ✅      |

❌   = Keine Lösung / No solution,
✅   = Voll gelöst / Fully solved,
(✅) = Erster Teil gelöst / First part solved

## Repository-Aufbau / Repository Structure
- Day-`XX`       (1) 
  - `language`        (2)
    - `user`    (3)
- shared        (4)
  - `user`
    - \*    

(1) Jeder Tag hat einen eigenen Ordner, der nach dem Schema `Day-XX` benannt ist, also beispielsweise `Day-09`.

(2) Jeder Tag hat Unterordner für jede Programmiersprache, die Beitragende zur Lösung dieses Tages verwendet haben. Dabei ist für den Namen des Ordners der kleingeschriebene Name der Sprache zu verwenden, also beispielsweise `java`.

(3) Mehrere Beitragende können einen Tag in der selben Programmiersprache lösen, weshalb jeder einen Ordner für seine Lösung erstellen sollte. Dieser enthält den Quellcode, welcher bestenfalls beide Teilaufgaben löst.

(4) Der *shared*-Ordner ist für alle zusätzlichen Dateien, die neben der Lösungsdatei benötigt werden, etwa Hilfscode oder geteilte Bibliotheken.

---

(1) Every day has its own directory named after the scheme `Day-XX`, so e.g. `Day-09`.

(2) Each day has a subfolder for every programming language used by contributors to solve that day. The name should be the lowercase name of the language, e.g. `java`. 

(3) Because multiple contributors may solve the same day in the same programming language, each user should create a directory for their solution. It contains the source code which solves both parts of the challenge at best.

(4) The *shared* directory is for all other files which are required aside from the solution file, e.g. utility code and shared libraries.

## Anleitung zum Beitragen / How to Contribute (only for DevCord members)
Wenn du zu den Lösungen beitragen möchtest, hast du zwei Optionen: **Selbst mit git arbeiten und Pull Requests erstellen** oder **deine Lösungen der Verwaltung dieses Repositories auf Discord schicken**. Wir empfehlen ersteres, wenn du schon mal git benutzt hast, und zweiteres, wenn du von diesem System nur Bahnhof verstehst.

### Pull Request erstellen

1. Forke dieses Repository in deinen GitHub-Account (oben rechts auf "Fork" klicken)
2. Klone das Repository bei dir lokal
3. Füge deine Lösungen hinzu und bearbeite sie - **beachte dabei die [Ordnerstruktur](#repository-aufbau--repository-structure)**
4. Bearbeite **nicht** die README! Die Tabelle wird von uns ergänzt.
5. Mache eine Pull Request wann immer du
   - neue funktionierende Lösungen hast
   - Lust drauf hast (du musst nicht jeden Tag eine Pull Request machen, sondern kannst dir auch Zeit damit lassen)

**Bitte achte darauf, dass die Lösungen nicht deine kompletten IDE-Dateien enthalten, sondern ausschließlich den relevanten Quellcode zur Lösung des Problems!**

### Lösungen an das Management geben
Schicke sie an einen der folgenden DevCord-User mit Informationen zu Sprache, Tag, evtl. benötigten zusätzlichen Dateien und Namen:
   - `das_#9677`
   - `Johnny#3826`
   
