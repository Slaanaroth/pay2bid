# Middleware Project - Pay2bid
Distributed auction house application - Middleware project, M2 ALMA 2016/2017   
**Auteurs** : Alexis Giraudet, Arnaud Grall, Thomas Minier

# Prerequisites
* Java version : 1.6 or newer
* Maven

# Installation

Navigate into the project directory and build it using Maven
```
cd pay2bid/
mvn package
mvn assembly:single
```

# Launch the Server
```
java -jar target/pay2bid-1.0-SNAPSHOT-jar-with-dependencies.jar -l
```

# Launch a Client
```
java -jar target/pay2bid-1.0-SNAPSHOT-jar-with-dependencies.jar
```
