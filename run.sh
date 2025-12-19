#!/bin/bash

# Build and run the JavaFX Mind Map application

echo "Building JavaFX Mind Map Application..."
mvn clean compile

if [ $? -eq 0 ]; then
    echo "Build successful! Starting application..."
    mvn javafx:run
else
    echo "Build failed. Please check the error messages above."
    exit 1
fi
