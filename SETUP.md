# Trajectory — Getting Set Up (IntelliJ + Maven)

The project has been converted to a standard Maven structure. This doc walks through what you need installed and how to open the project for the first time.

## 1. Pull the latest `main`

You'll see a new `pom.xml` at the project root, and `src/com/...` has moved to `src/main/java/com/...`. The old `lib/` folder (with the manually-downloaded SQLite JAR) is gone — Maven fetches dependencies automatically now.

## 2. Check your JDK

Open **File → Project Structure → Project SDK**. If it's not JDK 21 or newer, click **Add SDK → Download JDK**, select version 21, and apply. (If you're still on JDK 8 from a previous class, that's fine — this just adds a second JDK alongside it; IntelliJ will use whichever one the project is configured for.)

## 3. Open the project

Use **File → Open**, and select the **`pom.xml` file itself** — not just the folder. IntelliJ will detect it as a Maven project and prompt to import; say yes. It will automatically download SQLite JDBC and JavaFX the first time — no manual JAR downloads needed.

## 4. Enable the JavaFX plugin

Go to **Settings → Plugins → Installed**, confirm **JavaFX** is checked. It ships bundled with IntelliJ, just needs to be turned on — no marketplace download required.

## 5. Install Scene Builder

Download the Mac/Windows installer from [gluonhq.com/products/scene-builder](https://gluonhq.com/products/scene-builder). Once installed, point IntelliJ at it: **Settings → Languages & Frameworks → JavaFX**, set the Scene Builder path. After that, right-clicking any `.fxml` file gives an "Open in SceneBuilder" option.

## 6. Verify everything builds and runs

Open the Maven panel (right sidebar) → **Lifecycle → compile**. It should finish with `BUILD SUCCESS`.

To run the CLI driver, either use IntelliJ's run button on `Driver.java`, or from a terminal:
```
mvn compile
mvn exec:java
```
You should see `Please enter your name:` — if so, you're fully set up.

## Troubleshooting

**Red squiggly underlines on otherwise-correct imports/classes after pulling or restructuring:** this is usually a stale Java language server cache, not a real error. Try **Build → Rebuild Project**, or close and reopen the project. Don't accept any "move file to package" or "correct package declaration" quick-fix unless you've actually double-checked the file's real location is wrong — the cache is often wrong, not your code.

**`mvn` not recognized in a terminal:** Maven needs to be installed separately from any IDE plugin — on Mac, `brew install maven`; on Windows, see the [Apache Maven install docs](https://maven.apache.org/install.html). Note: having a "Maven for Java"-style plugin installed does *not* mean Maven itself is installed — the plugin just provides a UI around the `mvn` executable, which still has to exist on your system separately.

**Don't commit your own `.idea/` folder.** It's already in `.gitignore`, so this should happen automatically — but if you ever see `.idea/` appear as a change in your Git panel, don't stage or commit it. That's personal IDE config, not project code.

## Questions / issues

Reach out to Andrew if anything here doesn't match what you're seeing.
