javac -d bin -cp "./libs/Pokemon.jar" -sourcepath src src/s367945/lab1/SimpleTest.java
cd bin
jar cfm ../SimpleTest.jar ../manifest.mf *
cd ..
# java -cp "./libs/Pokemon.jar;bin" s367945/lab1/SimpleTest
java -jar SimpleTest.jar
