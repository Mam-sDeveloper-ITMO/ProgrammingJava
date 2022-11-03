javac -encoding "utf-8" -d bin -sourcepath src src/s367945/lab2/Main.java
jar cfm ./Main.jar ./manifest.mf -C bin .
java -jar Main.jar