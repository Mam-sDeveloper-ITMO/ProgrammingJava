javac -d bin -cp "./libs/Pokemon.jar" -sourcepath src src/s367945/lab1/SimpleTest.java
jar cfm ./SimpleTest.jar ./manifest.mf -C bin/ *
REM java -cp "./libs/Pokemon.jar;bin" s367945/lab1/SimpleTest
java -jar SimpleTest.jar
