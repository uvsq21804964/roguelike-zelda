[ENGLISH BELOW]

# Projet Roguelike

L'objet de ce projet a été de mettre en pratique les principes SOLID lors d'un cours universitaire.
J'ai décidé par moi-même de poursuivre son développement par pur plaisir de programmer.

Les personnages (caractéristiques et création) ainsi que les règles du jeu sont partiellement empruntés à [_Chroniques Oubliées Fantasy_](https://black-book-editions.fr/catalogue.php?id=477).
Le document qui a servi de base est le DRS ([_Document de Référence du Système_](https://www.co-drs.org/fr)).

# Description du jeu

Vous évoluez dans un étage de donjon remplis de PNJ pouvant être des ennemis ou des alliés selon votre capacité à les convaincre de rejoindre votre cause.
Il s'agit d'un jeu de combat avec des équipements comme des armures et des armes pouvant être achetés, rammasés au sol ou trouvés dans des coffres.
Votre mission est de vaincre tous les PNJs qui ne sont pas (devenus) vos alliés.


## Pour télécharger le jeu

1. Cliquer sur le bouton vert "Latest" dans la section "Releases":
![image](https://github.com/user-attachments/assets/90d13ae3-b4c0-4eb0-bbb2-420ce39329e5)
2. Cliquer sur le fichier se terminant par ".exe".
![image](https://github.com/user-attachments/assets/3f4d53d2-b77f-4770-b7bd-202c69b85297)

Si vous ne réussissez pas à suivre ce processus, vous pouvez simplement cliquez [ici](https://objects.githubusercontent.com/github-production-release-asset-2e65be/798802162/78f028a1-b088-4b64-a065-79d6caae2b01?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=releaseassetproduction%2F20241003%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20241003T133435Z&X-Amz-Expires=300&X-Amz-Signature=5c7092082e71e9ff931811e686ac86a87c29039dbc1b3cbbbf3a3857561ab6e6&X-Amz-SignedHeaders=host&response-content-disposition=attachment%3B%20filename%3DRoguelikeZeldaGame_by_Tom_Abbouz.exe&response-content-type=application%2Foctet-stream)

4. Vous recevrez sans doute une notification de votre navigateur interrompant le processus de téléchargement. 
Cela se produit souvent pour les nouveaux fichiers. Il vous suffit de dire à votre navigateur que vous souhaitez "conserver" le fichier pour pouvoir l'ouvrir.

## Pour lancer une partie
1. Cliquer sur le fichier que vous venez de télécharger (RoguelikeZeldaGame_by_Tom_Abbouz.exe)
2. La fenêtre du jeu s'ouvre.
3. Vous pouvez observer une entrée textuelle. Vous devez y écrire "entrer [votre pseudonyme]"
![image](https://github.com/user-attachments/assets/32fc65ef-1b73-4e3a-a35a-e20246f3d2cc)
4. Appuyez sur la touche "Entrée" de votre clavier.
5. Vous pouvez jouer! Bon jeu!
![image](https://github.com/user-attachments/assets/29243760-2a77-4b91-9024-1a12279fc48d)

## Comment jouer?

## IHM

L'interface utilisateur devra être conçue pour pouvoir être remplacée en fonction des besoins.
Les packages _personnage_ et _donjon_ sont totalement indépendants de l'IHM.
Le pattern _Commande_ a été utilisé pour connecter les actions de l'utilisateur avec les comportements des objets.

L'IHM rudimentaire utilise un mode textuelle.
La salle dans laquelle se trouve le PJ sera affichée sous la forme d'une grille $6 \times 6$ où chaque élément sera représenté par un caractère (les pièges non encore identifiés n'apparaîtront pas).
La saisie d'une commande se fait simplement sous la forme `action objet` (par exemple `ouvrir coffre`).

### Interactions

Le PJ peut effectuer les actions suivantes :
déplacer/sauter/franchir, ramasser, parler/soutirer/convaincre, attaquer, ouvrir/crocheter/forcer, étudier (porte/coffre pour déceler un piège).
Les actions franchir, soutirer, convaincre, crocheter et forcer peuvent échouées. Le PJ ayant des caractéristiques particulères (Force, Intelligence, ...), il devra parfois effectuer de nouvelles tentatives pour atteindre son but. Rarement, les [tests de caractéristique](https://www.co-drs.org/fr/jeu/regles/les-actions/resolution-dun-test) échoueront toujours si votre personnage à des points de caractéristiques trop insuffisants (par exemple si le PJ a un faible intellect, il ne pourra jamais convzincre les PNJ les plus têtus et de type __neutre__ de devenir son allié).

## Aspects techniques

- Le _build_ est assuré par Maven et plus précisément Maven wrapper (déjà intégré dans le projet).
  Aucune manipulation en dehors de Maven ne devra être nécessaire.
- La version de Java utilisée est la [version 17](https://adoptium.net/).
- Le _build_ intégre `checkstyle` pour la vérification des règles de codages Google.
- Quelques tests unitaires [JUnit 5](https://junit.org/junit5/docs/current/user-guide/) sont disponibles pour certaines méthodes.
- Un outil de [_Code Coverage_](https://fr.wikipedia.org/wiki/Couverture_de_code) est intégré au _build_
- Les fonctionnalités du langage Java suivantes ont été utilisées: POO, exceptions, librairie de collections, Input/Output, package.
- À la base, l'application était exécutée à partir d'un `jar` incluant toutes les dépendances. J'ai utilisé l'outil [Launch4j](https://launch4j.sourceforge.net/) pour le transformé en un fichier exécutable sur Mac, Linux et Windows.

## Personnages (PJ et PNJ)

Les personnages du jeu, Personnage Joueur (PJ) et Non Joueur (PNJ), sont représentés par leur nom, [6 caractéristiques](https://www.co-drs.org/fr/jeu/regles/creer-un-personnage/caracteristiques), leurs [Points de Vie](https://www.co-drs.org/fr/jeu/regles/creer-un-personnage/des-de-vie-et-points-de-vie) (PV), leur [Initiative](https://www.co-drs.org/fr/jeu/regles/creer-un-personnage/initiative) (Init), leur [Défense](https://www.co-drs.org/fr/jeu/regles/creer-un-personnage/defense)(DEF), leurs [scores d'attaque](https://www.co-drs.org/fr/jeu/regles/creer-un-personnage/attaque) et leur équipement. Tous ces éléments sont détaillés dans le DRS.

### Création

L'instanciation d'un personnage a été implémentée en utilisant le [pattern _Builder_](https://en.wikipedia.org/wiki/Builder_pattern).
Le constructeur prends en paramètre le nom du personnage et génère ses caractéristiques [aléatoirement](https://www.co-drs.org/fr/jeu/regles/creer-un-personnage/caracteristiques/determiner-les-scores/methode-aleatoire).

### Équipement

Les personnages disposent d'une bourse contenant des pièces d'argent et de divers [équipements](https://www.co-drs.org/fr/jeu/regles/creer-un-personnage/equipement) ([armes](https://www.co-drs.org/fr/jeu/regles/equipement/armes) et [armures](https://www.co-drs.org/fr/jeu/regles/equipement/armures)).
L'équipement peut être trouvé au cours de l'aventure ou acheté/vendu auprès des PNJ de type __amical__.
Les armes se catégorisent en trois groupes: armes au corps-à-corps, armes à distance à une main et armes à distance à deux mains.
Lorsque le personnage est équipé d'un bouclier, il a forcément une main indisponible pour un autre équipement.

### PNJ

En plus des caractéristiques communes à tous les personnages, les PNJ possèdent un comportement qui représente les actions que le PNJ va entreprendre.
Chaque PNJ devra avoir un profil de comportement :

- _agressif_, le PNJ attaquera le PJ;
- _defensif_, le PNJ garde un élément du jeu et attaquera le PJ s'il s'en approche;
- _neutre_, le PNJ ignore le PJ et se défendra s'il est attaqué;
- _amical_, le PNJ aide le PJ s'il est sollicité (information, équipement).

Un pattern de comportement a été utilisé pour implémenter les différentes IA des PNJ.

## Donjon

Le PJ évolue dans un étage d'un donjon.
Cet étage est composé de pièces rectangulaires de tailles aléatoires et séparées par des couloirs.
Une case peut être vide, contenir un item (trésor, équipement, …), un PNJ ou un piège.

### Génération des pièces

Plusieurs méthodes de génération de donjon ont été conçues grâce à l'utilisation du pattern [Stratégie](https://en.wikipedia.org/wiki/Strategy_pattern) (salles fixes avec contenu aléatoire, salles aléatoires et labyrinthe).

La méthode choisie permet de créer un donjon de 9 salles de tailles aléatoires et au contenu aléatoire. Il n'est pas possible de changer cette méthode bien que d'autres aient été mises en oeuvre.
