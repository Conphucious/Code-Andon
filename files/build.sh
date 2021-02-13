#!/bin/bash

# Arg0 [MANDATORY]: project path
# Arg1 [MANDATORY]: file name
# Arg3 [OPTIONAL]: mvn flags

stdError () {
    echo >&2 "$@"
    exit 1
}

[ "$#" -eq 2 ] || stdError "2 argument required, $# provided"
file=$1$2
grep -q -i -E '((.*?).log)|((.*?).txt)' <<< $file || stdError "Log or txt file required, $2 provided"


path=$1
fileName=$2

cd $path
mvn clean install > $2 $3
