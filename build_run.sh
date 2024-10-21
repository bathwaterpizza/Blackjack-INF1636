#!/bin/bash

# Define the project directory (assuming the script is in the project root)
PROJECT_DIR=$(pwd)

# Define the source and output directories
SRC_DIR="$PROJECT_DIR/src"
BIN_DIR="$PROJECT_DIR/bin"
LIB_DIR="$PROJECT_DIR/lib"

# Ensure the bin directory exists
mkdir -p "$BIN_DIR"

# Compile the Java files
echo "[BUILD] Compiling Java files.."
if [ -d "$LIB_DIR" ]; then
  # If lib directory exists, include external JARs in the classpath
  javac -d "$BIN_DIR" -cp "$LIB_DIR/*" $(find "$SRC_DIR" -name "*.java")
else
  # If no lib directory, just compile the source files
  javac -d "$BIN_DIR" $(find "$SRC_DIR" -name "*.java")
fi

# Check if compilation succeeded
if [ $? -ne 0 ]; then
  echo "[BUILD] Compilation failed"
  exit 1
fi

echo "[BUILD] Compilation successful"

# Find the main class (you need to update this with your actual main class)
MAIN_CLASS="main.Main" # Replace with your actual main class

# Run the project
echo "[BUILD] Running the project.."
if [ -d "$LIB_DIR" ]; then
  # If lib directory exists, include external JARs in the classpath
  java -cp "$BIN_DIR:$LIB_DIR/*" "$MAIN_CLASS"
else
  # If no lib directory, run with just compiled classes
  java -cp "$BIN_DIR" "$MAIN_CLASS"
fi
