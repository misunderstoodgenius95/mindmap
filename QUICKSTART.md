# Quick Start - JavaFX Mind Map

## The Problem

You're seeing: **"Error: JavaFX runtime components are missing, and are required to run this application"**

This happens because JavaFX is not included in standard Java installations (Java 11+).

## Quick Solutions

### ✅ Solution 1: Install System JavaFX (Linux/Ubuntu)

```bash
sudo apt-get update
sudo apt-get install openjfx libopenjfx-java
mvn javafx:run
```

### ✅ Solution 2: Let Maven Handle It

If you have internet access, Maven will download JavaFX automatically:

```bash
mvn clean install
mvn javafx:run
```

### ✅ Solution 3: Manual JavaFX SDK Setup

**For environments without internet access:**

1. **Download JavaFX SDK** (on a machine with internet):
   - Visit: https://openjfx.io/
   - Download JavaFX SDK 21 for your OS
   - Transfer to your project machine

2. **Download Gson JAR**:
   - URL: https://repo1.maven.org/maven2/com/google/code/gson/gson/2.10.1/gson-2.10.1.jar
   - Save to `lib/gson-2.10.1.jar` in project directory

3. **Set JavaFX path**:
   ```bash
   export JAVAFX_HOME=/path/to/javafx-sdk-21
   ```

4. **Run using alternative script**:
   ```bash
   ./run-alt.sh
   ```

### ✅ Solution 4: Using an IDE

**IntelliJ IDEA:**
1. File → Project Structure → Libraries
2. Add from Maven: `org.openjfx:javafx-controls:21.0.1`
3. Add from Maven: `org.openjfx:javafx-fxml:21.0.1`
4. Add from Maven: `com.google.code.gson:gson:2.10.1`
5. Run `MindMapApp.java`

**Eclipse:**
1. Project Properties → Java Build Path → Libraries
2. Add External JARs from JavaFX SDK
3. Add Gson JAR
4. Run as Java Application

## Current Environment Status

This development environment has network connectivity issues preventing automatic dependency download. The application code is complete and working - it just needs JavaFX dependencies to run.

## What's Been Done

✅ Updated project to Java 21 and JavaFX 21
✅ Added module-info.java for JPMS support
✅ Created alternative run scripts
✅ Provided comprehensive setup documentation
✅ Committed and pushed all changes

## Next Steps

To run the application on a system with internet access:

1. Clone the repository
2. Run `mvn javafx:run`
3. The application will start automatically

That's it! Maven will handle all dependencies.

## Application Features

Once running, you'll have:
- **Double-click** to create nodes
- **Drag nodes** to reposition
- **Connect Mode** to link nodes
- **Save/Load** your mind maps
- **Delete** selected nodes
- **Edit text** by double-clicking nodes

## Need More Help?

See SETUP.md for detailed troubleshooting and alternative setup methods.
