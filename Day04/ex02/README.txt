1. Create dir target/ and lib/

mkdir target mkdir lib

2. Download libs

curl https://repo1.maven.org/maven2/com/beust/jcommander/1.72/jcommander-1.72.jar -o lib/jcommander-1.72.jar

curl https://repo1.maven.org/maven2/com/diogonunes/JCDP/4.0.0/JCDP-4.0.0.jar -o lib/JCDP-4.0.0.jar

3. Extract files for further usage

cd target

jar xf ../lib/jcommander-1.72.jar
jar xf ../lib/JCDP-4.0.0.jar

rm -rf META-INF
cd ..

4. Compile

javac -cp "lib/jcommander-1.72.jar:lib/JCDP-4.0.0.jar" src/java/edu.school21.printer/app/Program.java src/java/edu.school21.printer/logic/ImageConverter.java src/java/edu.school21.printer/logic/Args.java -d ./target


5. Copy resources

cp -r src/resources ./target/resources

6. Create jar file

jar cfm target/images-to-chars-printer.jar src/manifest.txt -C target .

7. RUN

java -jar target/images-to-chars-printer.jar --white=RED --black=GREEN