#!/bin/bash

tree -f -i -P *.java | grep -F .java > compile.list

javac @compile.list -d bin/
