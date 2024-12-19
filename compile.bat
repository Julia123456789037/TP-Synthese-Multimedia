Get-ChildItem -Recurse -Filter *.java | Select-Object -ExpandProperty FullName > compile.list

javac @compile.list -d bin/