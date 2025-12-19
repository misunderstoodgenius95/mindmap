#!/bin/bash

# Alternative run script for JavaFX Mind Map
# This script helps you run the application if Maven dependencies are not available

echo "JavaFX Mind Map - Alternative Run Script"
echo "========================================"
echo ""

# Check if JAVAFX_HOME is set
if [ -z "$JAVAFX_HOME" ]; then
    echo "ERROR: JAVAFX_HOME environment variable is not set."
    echo ""
    echo "Please download JavaFX SDK from: https://openjfx.io/"
    echo "Then set JAVAFX_HOME to point to the JavaFX SDK directory."
    echo ""
    echo "Example:"
    echo "  export JAVAFX_HOME=/path/to/javafx-sdk-21"
    echo "  ./run-alt.sh"
    echo ""
    exit 1
fi

# Check if Gson JAR exists
if [ ! -f "lib/gson-2.10.1.jar" ]; then
    echo "ERROR: Gson library not found at lib/gson-2.10.1.jar"
    echo ""
    echo "Please download Gson from: https://repo1.maven.org/maven2/com/google/code/gson/gson/2.10.1/gson-2.10.1.jar"
    echo "And place it in the lib/ directory"
    echo ""
    mkdir -p lib
    exit 1
fi

echo "Compiling application..."
javac --module-path "$JAVAFX_HOME/lib" \
      --add-modules javafx.controls,javafx.fxml \
      -cp "lib/gson-2.10.1.jar" \
      -d out/production \
      src/main/java/module-info.java \
      src/main/java/com/mindmap/*.java

if [ $? -eq 0 ]; then
    echo "Compilation successful!"
    echo "Running application..."
    echo ""

    # Copy resources
    mkdir -p out/production
    cp -r src/main/resources/* out/production/ 2>/dev/null || true

    java --module-path "$JAVAFX_HOME/lib:lib/gson-2.10.1.jar:out/production" \
         --add-modules javafx.controls,javafx.fxml,com.google.gson \
         -m com.mindmap/com.mindmap.MindMapApp
else
    echo "Compilation failed!"
    exit 1
fi
