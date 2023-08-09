#1. Create directory

mkdir target

#2. Compile

javac -d target src/java/edu.school21.printer/app/Program.java src/java/edu.school21.printer/logic/ImageConverter.java

#3. Create resources/ in target/

mkdir -p target/resources

#4. Copy resources from src/ to resources/ and change directory to target/

cp src/resources/* target/resources/

cd target

#5. Create archive

jar cvfe images-to-chars-printer.jar edu.school21.printer.app.Program edu/school21/printer/

#6. Run 

java -jar images-to-chars-printer.jar . 0 resources/it.bmp
