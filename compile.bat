Get-ChildItem -Recurse -Filter *.java | Select-Object -ExpandProperty FullName > compile.list

javac @compile.list -d bin/

cd bin | jar cfe SwingPainter.jar org.multimedia.main.Controleur org/multimedia/main/Controleur.class *

java -jar SwingPainter.jar