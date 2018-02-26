#!/bin/bash 

javaCommand="$(javac -version)"
if [[ "$javaCommand" == *"command not found"* ]]; then
	echo "You do not have the java compiler installed on your machine"
else 
	echo "You can compile"
	cd ../../src/java
	find -name "*.java" > sources.txt
	javac @sources.txt
fi
