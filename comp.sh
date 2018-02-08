#!/bin/bash
timestamp=$(date +%s)
echo "starting run script, with timestamp:"
echo $timestamp
mkdir out/$timestamp
find . -name "*.class" -type f -delete
javac runner.java
echo "Completed compile portion"

java runner $timestamp 43 67 55 treble
echo "Completed run"
cd out/$timestamp

/Applications/Lilypond.app/Contents/Resources/bin/lilypond ReflectionsScore.ly
open ReflectionsScore.pdf
open ReflectionsScore.midi
cd ..
cd ..
find . -name "*.class" -type f -delete

