# Fonctionnement de la connexion à la Base de données (RMI)

Ce projet utilise une architecture Java RMI pour exposer les services de gestion des restaurants et des réservations à des clients distants.

## Architecture de la base de données

- La base de données Oracle contient les tables principales liées aux restaurants et aux réservations.
- Le fichier `data/db_properties/db.properties` stocke les paramètres de connexion : `db.user`, `db.password` et `db.url`.
- La connexion JDBC est gérée par `src/ServerRMI/DatabaseConnection.java`.

## Composants RMI

- `ServeurRestauration.java` : point d’entrée du serveur RMI.
- `ServiceRestauration.java` : implémentation du service exposé aux clients.
- `Repository.java` : couche d’accès aux données utilisée par le service.

## Principales méthodes et fonctionnalités

- `DatabaseConnection.getConnection()` : établit la connexion JDBC à Oracle en lisant `db.properties`.
- `Repository.getRestaurants()` : récupère la liste des restaurants disponibles.
- `Repository.getReservations()` : récupère les réservations existantes depuis la base de données.
- `ReservationHandler` / `GetRestosHandler` / `GetVelibsHandler` : handlers API qui utilisent les services Java pour répondre aux requêtes HTTP.
- `Reservation.java` et `Restaurant.java` : modèles de données représentant les entités de la base.

## Flux général

1. Le serveur RMI démarre via `MainServeurRestauration.java`.
2. `ServiceRestauration` utilise `Repository` pour exécuter les opérations SQL.
3. `DatabaseConnection` ouvre et gère la connexion Oracle.
4. Les clients distants appellent les méthodes RMI pour lire ou écrire des données.

# Configuration de la connexion à la Base de données

## 1. Ajouter ojdbc11.jar au path de IntelliJ 
- Clic droit sur SAE_web_RMI, Open Module Settings
- Dans la section Dependencies (à droite), appuyer sur "+" et choisir JARs or Directories
- Sélectionner ojdbc11.jar (data/oracle_jdbc), le sélectionner dans les dépendences et Apply

## 2. Ajouter vos identifiants de connexion
- Créer le fichier db.properties dans data/db_properties
- Ajouter les éléments suivants à remplacer avec vos identifiants :

 > db.user=eXXXXXu
 >
 > db.password=PASSWORD
 >
 > db.url=jdbc:oracle:thin:@charlemagne.iutnc.univ-lorraine.fr:1521:infodb


# Features

    - BD Restaurant/Resa 
    - Accès api VLib
    - Affichage LeafLet 
    - Implémentation API en java
    - Server RMI pour gestion de données


# Utilisation de l'API Java

## Grace à l'API Java, on peut accéder à plusieurs routes : 

- route /velib (GET): permet de récupérer la liste des vélibs de nancy, la réponse est un json comprennant un tableau de stations avec les diverses informations
- route /travaux (GET): permet d'avoir la liste des travaux des restos de nancy, la réponse est un json comprennant un tableau de travaux 
- route /getResots (GET): permet de récupérer la liste des réstaurants de notre BD
- route /reserver (POST): permet de réserver une table dans un restaurant 