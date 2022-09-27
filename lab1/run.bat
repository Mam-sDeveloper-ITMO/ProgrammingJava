javac -d bin -cp "./libs/Pokemon.jar" -sourcepath src src/s367945/lab1/SimpleTest.java            
@REM java -cp "./libs/Pokemon.jar;bin" s367945/lab1/SimpleTest
CD bin
jar cfm ../SimpleTest.jar ../manifest.mf *
CD ..
java -jar SimpleTest.jar