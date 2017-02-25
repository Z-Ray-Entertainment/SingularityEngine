#!/bin/sh

ROOT=
THIRD="$ROOT"third-party
LIBS="$ROOT"libs
TEST="$ROOT"test

#JAVA="java -agentlib:yjpagent"
JAVA=java

isKernel64Bit=false
if [ $(uname -m) = "x86_64" ]; then
    isKernel64Bit=true
fi

isJava64Bit=false
if [ ! -z "$(java -version 2>&1 | grep "64-Bit")" ]; then
    isJava64Bit=true
fi

ARCH="lin32"
if [ $isKernel64Bit = true -a $isJava64Bit = true ]; then
    ARCH="lin64"
    echo "64-Bit Kernel and Java detected!"
    echo "Running SingularityEngine with 64-Bit natives..."
elif [ $isJava64Bit = true ]; then
    echo "64-Bit Java detected, but no 64-Bit Kernel. this won't work!"
    echo "exiting"
    exit
elif [ $isKernel64Bit = true ]; then
    echo "64-Bit Kernel and 32-Bit Java detected!"
    echo "Running SingularityEngine with 32-Bit natives..."
else
    echo "32-Bit Kernel and Java detected!"
    echo "Running SingularityEngine with 32-Bit natives..."
fi

java -Djava.library.path=$ARCH -jar "./SingularityEngine.jar"
