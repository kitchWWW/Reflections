#!/bin/bash
find . -name "*.class" -type f -delete
javac runner.java
echo "Compiled"