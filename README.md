# Middleware Project - Pay2bid
Distributed auction house application - Middleware project, M2 ALMA 2016/2017   
**Auteurs** : Alexis Giraudet, Arnaud Grall, Thomas Minier
**Contributeurs** : Aurélien Brisseau, Théo Dolez, Laurent Girard, Florent Mercier

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

# TO DO

- [ ] Le client proposant l'enchère ne devrait pas pouvoir bid sur celle-ci
- [x] identificateurs pour les ≠ clients
- [ ] séparation par thèmes
- [x] qui gagne en cas d'égalité -> Random selon le hashcode
- [x] enchère négatives permises
- [ ] anciennes enchères se relancent avec les nouvelles --> fieldtext de l'ancienne enchère réapparait
- [ ] finir l'enchère quand tout le monde a bid sans attendre fin du timer
- [ ] gestion des déconnexions pendant enchère --> timeElapsed non appelé par le client qui se déconnecte, l'enchère ne s'arrête jamais
- [x] durant l'enchère on ne sait pas qui l'emporte
- [x] erreur si personne ne bid au premier tour
- [ ] correction options host et port
