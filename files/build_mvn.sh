#!/bin/bash

# Arg0 [MANDATORY]: project path
# Arg1 [MANDATORY]: build log file path
# Arg3 [OPTIONAL]: boolean value for ignoring warnings

stdError () {
    echo >&2 "$@"
    exit 1
}

[ "$#" -ge 2 ] || stdError "2 argument required, $# provided"
file=$1$2
grep -q -i -E '((.*?).log)|((.*?).txt)' <<< $file || stdError "Log or txt file required, $2 provided"


path=$1
logPath=$2

cd $path

if [ "$3" == "true" ]; then
    ((mvn clean install | grep -v "WARNING") > $logPath)
else
    (mvn clean install > $logPath)
fi
