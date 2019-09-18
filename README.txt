Voici le modèle de projet pour créer l'API Web pour le projet de maintenance de matériel réseau.

Ce projet contient deux ressources Web de démo pour vérifier le bon fonctionnement du projet.
Ces ressources sont accessibles depuis un serveur Tomcat de test aux URI :

http://localhost:8080/resoapi/api/hello
http://localhost:8080/resoapi/api/hellobdd

La seconde accède à une base de données.

=============================================
Configuration de l'accès à la base de données
=============================================

L'accès à la base de données se fait par une datasource qui est configurée dans le fichier du projet

src/main/webapp/META-INF/context.xml

Vous devez adapter ce fichier pour positionner la bonne adresse de base de données ainsi que le bon login et le bon mot de passe.

=====================
Ajout du driver MySQL
=====================

Vous devez ajouter le driver JDBC pour MySQL dans le répertoire lib du répertoire
d'installation de votre serveur Tomcat. Le driver MySQL 5 est disponible à
https://repo1.maven.org/maven2/mysql/mysql-connector-java/5.1.48/mysql-connector-java-5.1.48.jar

Si vous utilisez MySQL 8 le driver est disponible à
https://repo1.maven.org/maven2/mysql/mysql-connector-java/8.0.17/mysql-connector-java-8.0.17.jar

Pour MySQL 8, le nom du driver doit être com.mysql.cj.jdbc.Driver dans la configuration de la datasource.
Pour éviter une erreur de connexion, il est parfois nécessaire de préciser le fuseau horaire à utiliser par le serveur en passant le paramètre serverTimezone dans l’URI JDBC.
Par exemple : jdbc:mysql://localhost:3306/maBase?serverTimezone=GMT

================
Support de cours
================

Le chapitre sur le développement d'API Web avec JAX-RS est disponible sur
https://spoonless.github.io/udev-2019/javaee_web/jaxrs.html
