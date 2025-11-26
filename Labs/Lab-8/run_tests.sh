#!/bin/bash

# Lab 8 - JUnit Test Runner Script
# This script compiles and runs all JUnit tests

echo "Lab 8 - Unit Testing"
echo ""

# Navigate to the Lab-8 directory
cd "$(dirname "$0")"

echo "Step 1: Compiling Java files..."
javac -cp .:junit-platform-console-standalone.jar Arthmatic.java CollectionOfFunctions.java ArthrmaticTest.java CollectionOfFunctionsTest.java

if [ $? -eq 0 ]; then
    echo "✓ Compilation successful!"
    echo ""
else
    echo "✗ Compilation failed!"
    exit 1
fi

echo "Step 2: Running JUnit tests..."
echo ""

java -jar junit-platform-console-standalone.jar --class-path . --scan-class-path

echo ""
echo "Test execution complete!"