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
