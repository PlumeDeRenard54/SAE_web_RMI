# Partie ClientHTTP - connexion à la Base de données (RMI)

Cette partie du projet utilise une architecture Java RMI pour exposer les services de gestion des restaurants et des réservations au serveur. De plus, elle permettra de faire des appels à des api externes. 

## Architecture de la base de données

- La base de données Oracle contient les tables RESTAURANT et RESERVATION.
- Le fichier `data/db_properties/db.properties` stocke les paramètres de connexion : `db.user`, `db.password` et `db.url`.
- La connexion JDBC est gérée par `src/ServerRMI/DatabaseConnection.java`.

#### TABLE RESTAURANT

| column    | type               |
|-----------|--------------------|
| ID        | 	NUMBER(38,0)      |
| NOM       | 	VARCHAR2(30 BYTE) |
| ADRESSE	  | VARCHAR2(100 BYTE) |
| LAT	      | NUMBER(12,8)       |
| LON	      | NUMBER(12,8)       |
| NBTABLES	 | NUMBER(38,0)       |

#### TABLE RESERVATION

| column       | type               |
|--------------|--------------------|
| IDRES        | 	NUMBER(38,0)      |
| NOMCLI       | 	VARCHAR2(30 BYTE) |
| PRENOMCLI    | 	VARCHAR2(30 BYTE) |
| NUMTEL       | 	VARCHAR2(30 BYTE) |
| IDRESTAURANT | 	NUMBER(38,0)      |
| DATERES      | 	DATE              |
| NBCONVIVES   | 	NUMBER(38,0)      |

## Composants RMI

- `ServeurRestauration.java` : objet distant qu'on exporte sur le port 1099.
- `ServiceRestauration.java` : interface du service exposé aux clients.
- `Repository.java` : patron de conception Repository pour l'accès à la base de données Oracle par ServeurRestauration. C'est un Singleton.

## Principales méthodes et fonctionnalités

- `DatabaseConnection.getConnection()` : établit la connexion JDBC à Oracle en lisant `db.properties`.
- `Repository.getRestaurants()` : retourne la liste des restaurants.
- `Repository.getReservationPossoble()` : retourne vrai si une réservation est possible sur un créneau souhaité (à deux heures près), faux sinon.
- `Reservation.java` et `Restaurant.java` : modèles de données représentant les entités de la base.
- `reserverRestaurant.java` : réserve un restaurant s'il y a de la place disponible. La méthode est synchronized, cela garantit que deux réservations se feront toujours l'une après l'autre. Ainsi, aucune réservation ne se glissera entre le moment de la vérification de la disponibilité du crénaux et la réservation.
## Flux général

1. Le serveur RMI démarre via `MainServeurRestauration.java`.
2. `ServeurRestauration` utilise `Repository` pour exécuter les opérations SQL.
3. `DatabaseConnection` ouvre et gère la connexion Oracle.
4. Le serveur Http appelle les méthodes RMI pour lire ou écrire des données.

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

# Partie web:

## Affichage

> Un menu déroulant donne accès à plusieurs checkboxes permettant de filtrer les données par type d'élément recherché.

### Carte

>A l'aide de la librairie leaflet nous anvons implémenté une carte intéractive basée sur les cartes OpenStreetMap disponibles librement. Celle ci est centrée sur Nancy et affiche des icones signifiant les emplacements de stations de vélo de ville, de travaux/ emplacements d'accidents ainsi que de restaurants.

>lors de l'appui sur un des marqueurs, soit une popup résumant les données importantes sur l'emplacement s'ouvre soit, lors de l'appui sur un restaurant, une redirection vers une  autre page est faite.

### Liste de vignettes

> Dans un autre onglet, nous avons implémenté une liste. Celle ci affiche chaque lieu avec leur type et une description plus complète, dans chacune des vignettes il y a un bouton redirigeant vers la carte centrée sur l'emplacement lié à celle ci. Les réstaurants eux bénéficient d'un lien vers les réservations.


## Réservations

> Après avoir été redirigé depuis un restaurant, vous pouvez réserver une ou plusieurs tables. Pour cela vous vous trouvez face à un formulaire très complexe avec plusieurs champs

| data | type |
| ----------- | ----------- |
| Date de réservation | Date |
| Heure de réservation | Heure |
| Nom | String |
| Prénom | String |
| Numero de téléphone | String |
| Nombre de places demandées | number |

> Suite au remplissage et à la validation des données, elles sont envoyées au serveur via une requete en POST à l'API pour être verifiées et validées. Dans le cas ou elles ne sont pas approuvées, une alerte est lancée à l'utilisateur.

## Compte Rendu

> Un dernier onglet contient une retranscription du fichier markdown faite par la librairie ShowDown.


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

## Fonctionnement du serveur Http

Le main du serveur Http possède un client Http en attribut. La méthode getInfosAPI prend une uri en paramètre et retourne la réponse du serveur (vélib, travaux ou restos).

## Comment fonctionne l'API ? 

L'API java repose sur un main qui créé un objet HttpServer. 
On associe à ce server plusieurs routes qui permettront d'obtenir les diverses informations voulues (accidents, vélib, restaurants, ...). 
Si on veut ajouter une nouvelle route, il suffit simplement d'ajouter 