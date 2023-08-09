mkdir target

javac -d target src/java/edu.school21.printer/app/Program.java src/java/edu.school21.printer/logic/ImageConverter.java

java -cp target/ edu.school21.printer.app.Program . 0 /Users/sabinaasp/Desktop/S21_JAVA/GITHUB/s21_java_bootcamp/Day04/ex00/ImagesToChar/src/resources/it.bmp