# PRÀCTICA 1: SERVLETS (JAVA)

Web-application usant Servlets de Java, JSON i Docker

## Notes abans de començar

* Per a provar el programa en una consola: compila la aplicació en java, crea un container Docker i corre l'aplicació en el Docker.
* Accedeix a [localhost/carrental_home](http://localhost:8080/my_webapp/carrental_home.html)

### Lectures recomenades
* [Enunciat de la pràctica original](https://gitlab.fib.upc.edu/pti/pti/tree/master/p1_servlets)
* [Documentació Docker](https://www.docker.com/resources)
* [Documentació Servlets](https://docs.oracle.com/javaee/7/api/javax/servlet/package-summary.html)
* [Documentació JSON](https://docs.oracle.com/javaee/7/api/javax/json/JsonObject.html)

## Deployment

Compila l'aplicació en java executant el fitxer _compile-java_
```
./compile-java
```
O bé executa:

```
javac -cp lib/servlet-api.jar:my_webapp/WEB-INF/lib/json-simple-1.1.1.jar my_webapp/WEB-INF/classes/mypackage/*.java
```
Fes build del container Docker
```
docker build -t tomcatcustom:latest .
```
Seguit de Docker run per a posar-lo en marxa
```
docker run --name tomcat -d -v ./my_webapp:/usr/local/tomcat/webapps/my_webapp -p 8080:8080 -p 8443:8443 tomcatcustom
```

Alternativament pots fer-ho tot de cop executant el fitxer _run-docker_
```
./run-docker
```

## Built With

* [JSON](https://docs.oracle.com/javaee/7/api/javax/json/JsonObject.html) - Per a guardar dades resultants de les operacions fetes a la web
* [Java Servlets](https://docs.oracle.com/javaee/7/api/javax/servlet/package-summary.html) - Per implementar i tractar requests de l'aplicació web
* [Docker](https://www.docker.com/) - Per generar els containers on executem el codi
* HTML/XML - Per a generar els formularis web

## Autors
* [Marc Catrisse Pérez](https://github.com/TheMatrix97)
* [Lluís Marquès i Peñaranda](https://github.com/CLKBlu3)
* [Marc Blanca Tabeni](https://github.com/MarcBT6)
