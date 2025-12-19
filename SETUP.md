# Setup Guide - JavaFX Mind Map

This guide will help you set up and run the JavaFX Mind Map application.

## Issue: "JavaFX runtime components are missing"

This error occurs when JavaFX modules are not available in your Java runtime. Here are the solutions:

## Solution 1: Use Maven (Recommended)

If you have internet access, Maven will automatically download JavaFX dependencies:

```bash
mvn clean javafx:run
```

## Solution 2: Manual JavaFX Setup

If Maven cannot download dependencies due to network issues:

### Step 1: Download JavaFX SDK

1. Go to https://openjfx.io/
2. Download JavaFX SDK 21 for your platform (Linux/Mac/Windows)
3. Extract it to a location, e.g., `/opt/javafx-sdk-21` or `C:\javafx-sdk-21`

### Step 2: Set Environment Variable

**Linux/Mac:**
```bash
export JAVAFX_HOME=/path/to/javafx-sdk-21
```

**Windows:**
```cmd
set JAVAFX_HOME=C:\path\to\javafx-sdk-21
```

### Step 3: Download Gson Library

1. Download Gson from: https://repo1.maven.org/maven2/com/google/code/gson/gson/2.10.1/gson-2.10.1.jar
2. Create a `lib` directory in the project root
3. Place `gson-2.10.1.jar` in the `lib` directory

### Step 4: Run the Alternative Script

```bash
./run-alt.sh
```

## Solution 3: Run Directly with Java

If you have JavaFX SDK and Gson downloaded:

```bash
# Compile
javac --module-path $JAVAFX_HOME/lib \
      --add-modules javafx.controls,javafx.fxml \
      -cp lib/gson-2.10.1.jar \
      -d out \
      src/main/java/com/mindmap/*.java

# Run
java --module-path $JAVAFX_HOME/lib:lib/gson-2.10.1.jar:out \
     --add-modules javafx.controls,javafx.fxml \
     -cp out \
     com.mindmap.MindMapApp
```

## Solution 4: Use a Pre-configured IDE

### IntelliJ IDEA
1. Open the project in IntelliJ IDEA
2. File → Project Structure → Libraries
3. Click "+" and add "From Maven"
4. Search for `org.openjfx:javafx-controls:21.0.1`
5. Add `javafx.controls`, `javafx.fxml`, and `com.google.code.gson:gson:2.10.1`
6. Run the main class: `com.mindmap.MindMapApp`

### Eclipse
1. Open the project in Eclipse
2. Right-click project → Properties → Java Build Path → Libraries
3. Add External JARs from JavaFX SDK lib folder
4. Add Gson JAR
5. Run as Java Application

## Verifying Your Setup

After successful setup, you should see:
- A window titled "Mind Map - JavaFX"
- A toolbar with buttons (New Node, Delete Selected, etc.)
- A white canvas where you can double-click to create nodes

## Troubleshooting

### "Module not found" error
- Make sure the module-path includes JavaFX lib directory
- Verify JavaFX SDK is correctly downloaded and extracted

### "ClassNotFoundException: com.google.gson.*"
- Download Gson JAR and add it to classpath
- Verify the JAR is in the lib directory

### "Could not find or load main class"
- Check that the main class is `com.mindmap.MindMapApp`
- Verify compilation completed successfully

## Quick Test

To verify JavaFX is available:

```bash
java --module-path $JAVAFX_HOME/lib \
     --add-modules javafx.controls \
     --version
```

This should show the Java version without errors.

## Need Help?

If you continue to experience issues, please check:
1. Java version (should be 11 or higher): `java -version`
2. Maven version (should be 3.6+): `mvn -version`
3. JavaFX SDK is properly extracted
4. Environment variables are set correctly
