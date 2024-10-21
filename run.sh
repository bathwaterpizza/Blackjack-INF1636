#!/bin/bash

# Define the project directory (assuming the script is in the project root)
PROJECT_DIR=$(pwd)

# Define the source and output directories
BIN_DIR="$PROJECT_DIR/bin"
LIB_DIR="$PROJECT_DIR/lib"

# Find the main class (you need to update this with your actual main class)
MAIN_CLASS="main.Main" # Replace with your actual main class

# Run the project
echo "[RUN] Running the project.."
if [ -d "$LIB_DIR" ]; then
  # If lib directory exists, include external JARs in the classpath
  java -cp "$BIN_DIR:$LIB_DIR/*" "$MAIN_CLASS"
else
  # If no lib directory, run with just compiled classes
  java -cp "$BIN_DIR" "$MAIN_CLASS"
fi
