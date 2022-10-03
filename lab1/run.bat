javac -d bin -cp "./libs/Pokemon.jar" -sourcepath src src/s367945/lab1/SimpleTest.java
CD bin
jar cfm ../SimpleTest.jar ../manifest.mf *
CD ..
@REM java -cp "./libs/Pokemon.jar;bin" s367945/lab1/SimpleTest
java -jar SimpleTest.jar