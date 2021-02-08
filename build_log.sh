#!/bin/bash
# https://www.baeldung.com/linux/use-command-line-arguments-in-bash-script
# Make first cli arg -path -P
# make second cli arg the file path which we take in and read back (saved from last run. Maybe model?).

cd $1
mvn clean install >> "test.txt"

