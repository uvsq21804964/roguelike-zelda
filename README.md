# Projet Roguelike

L'objet de ce projet est de revisiter la conception du projet Roguelike initié au premier semestre.

Les personnages (caractéristiques et création) ainsi que les règles du jeu seront partiellement empruntés à [_Chroniques Oubliées Fantasy_](https://black-book-editions.fr/catalogue.php?id=477).
Le document qui servira de base est le DRS ([_Document de Référence du Système_](https://www.co-drs.org/fr)).

## Contraintes techniques
* Ce mini-projet est à réaliser en Java __par groupe de 2 étudiants__.
Aucun autre format de groupe ne sera accepté sans justification et sans accord préalable de l'enseignant.
* Vous utiliserez `git` en effectuant des commits réguliers comportant des messages informatifs.
* Le _build_ sera assuré par Maven et plus précisément Maven wrapper (déjà intégré dans le projet).
  Aucune manipulation en dehors de Maven ne devra être nécessaire.
* La version de Java à utiliser est la [version 17](https://adoptium.net/).
* Le _build_ intégre `checkstyle` pour la vérification des règles de codages Google (déjà intégré dans le projet).
  Le projet devra donc les respecter.
* Des tests unitaires [JUnit 5](https://junit.org/junit5/docs/current/user-guide/) devront être disponibles pour la plupart des méthodes développées.
* Un outil de [_Code Coverage_](https://fr.wikipedia.org/wiki/Couverture_de_code) est intégré au _build_
* Les fonctionnalités du langage Java devront être utilisées au mieux (POO, exceptions, librairie de collections, I/O, …).
* L'application devra pouvoir être exécutée à partir d'un `jar` incluant toutes les dépendances (déjà intégré dans le projet).

## Personnages (PJ et PNJ)
Les personnages du jeu, Personnage Joueur (PJ) et Non Joueur (PNJ), sont représentés par leur nom, [6 caractéristiques](https://www.co-drs.org/fr/jeu/regles/creer-un-personnage/caracteristiques), leurs [Points de Vie](https://www.co-drs.org/fr/jeu/regles/creer-un-personnage/des-de-vie-et-points-de-vie) (PV), leur [Initiative](https://www.co-drs.org/fr/jeu/regles/creer-un-personnage/initiative) (Init), leur [Défense](https://www.co-drs.org/fr/jeu/regles/creer-un-personnage/defense)(DEF), leurs [scores d'attaque](https://www.co-drs.org/fr/jeu/regles/creer-un-personnage/attaque) et leur équipement. Tous ces éléments sont détaillés dans le DRS.
Pour simplifier, les notions de _Race_, _Profil_, _Voie_, _Magie_, _Progression et niveaux_ sont ignorées.

L'ensemble des éléments devront se trouver dans le package `personnage`.

### Représentation
1. Définir l'énumération `Caracteristique` permettant de représenter les 6 caractéristiques.
2. Définir la classe immuable `ScoreDeCaracteristique` représentant la valeur d'une caractéristique.
Elle devra posséder les méthodes `val()` et `mod()` retournant respectivement la valeur et le [modificateur](https://www.co-drs.org/fr/jeu/regles/creer-un-personnage/caracteristiques/modificateurs-de-caracteristiques) de la caractéristique.
De plus, la classe proposera un constructeur prenant en paramètre la valeur de la caractéristique (qui devra être validée).
3. Créer la classe `Personnage` contenant les accesseurs pour les différents éléments (scores de caractéristique, PV, Init, DEF, scores d'attaque).

### Création
L'instanciation d'un personnage sera implémentée en utilisant le [pattern _Builder_](https://en.wikipedia.org/wiki/Builder_pattern).

1. le constructeur prendra en paramètre le nom du personnage et générera les caractéristiques [aléatoirement](https://www.co-drs.org/fr/jeu/regles/creer-un-personnage/caracteristiques/determiner-les-scores/methode-aleatoire).
Un ordre de priorité par défaut sera défini entre les caractéristiques (la valeur la plus élevée dans la caractéristique la plus prioritaire, …).
La somme des valeurs des caractéristiques devra être comprise entre _65_ et _80_ afin d'éviter les personnages trop faibles ou trop puissants.
2. la méthode `priorite` prendra en paramètre l'ordre de priorité entre les caractéristiques et réordonnera les valeurs.
   L'ordre des caractéristiques devra être validé (ordre total, une seule fois chaque caractéristique).
3. la méthode `valeur` prendra en paramètre une caractéristique et une valeur et remplacera le score de cette caractéristique.
   La valeur devra être validée. 

### Équipement
Les personnages disposent d'une bourse contenant des pièces d'argent (PA) et de divers [équipements](https://www.co-drs.org/fr/jeu/regles/creer-un-personnage/equipement).
Seules les [armes](https://www.co-drs.org/fr/jeu/regles/equipement/armes) et [armures](https://www.co-drs.org/fr/jeu/regles/equipement/armures) sont à prendre en compte.
L'équipement pourra être trouvé au cours de l'aventure ou acheté/vendu auprès des PNJ.

### PNJ
En plus des caractéristiques communes à tous les personnages, les PNJ possèdent un comportement qui représente les actions que le PNJ va entreprendre.
Chaque PNJ devra avoir un profil de comportement :

- _agressif_, le PNJ attaquera le PJ;
- _defensif_, le PNJ garde un élément du jeu et attaquera le PJ s'il s'en approche;
- _neutre_, le PNJ ignore le PJ et se défendra s'il est attaqué;
- _amical_, le PNJ aidera le PJ (information, équipement, .
- …

Vous utiliserez le _pattern de comportement_ adapté pour cela.

## Donjon
Le PJ évolue dans un étage d'un donjon.
Cet étage est composé de pièces carrées de tailles identiques (8m de côté).
L'intérieur d'une pièce (sol) est découpé en cases carrées (1.5m de côté).
Il y a donc $6 \times 6$ cases dans une pièce.
Une case peut être vide, contenir un item (trésor, équipement, …), un PNJ ou un piège.

### Génération des pièces
Plusieurs méthodes de génération de donjon sont envisageables.
Pour permettre l'implémentation de plusieurs de ces méthodes, le pattern [Stratégie](https://en.wikipedia.org/wiki/Strategy_pattern) sera utilisé.

La première méthode permettra de créer un donjon prédéfini (pas de génération aléatoire) de 9 salles ($3 \times 3$).

D'autres méthodes de génération peuvent être ajoutées (salles fixes avec contenu aléatoire, salles aléatoires, labyrinthe).
La génération aléatoire est optionnelle mais le pattern permettant de la mettre en place doit être implémenté.

## IHM
L'interface utilisateur devra être conçue pour pouvoir être remplacée en fonction des besoins.
Une attention particulière devra être portée au fait de ne pas rendre les packages _personnage_ et _donjon_ dépendant de l'IHM.
Le pattern _Commande_ devra être utilisé pour connecter les actions de l'utilisateur avec les comportements des objets.

Une IHM rudimentaire uniquement en mode texte sera implémentée.
La salle dans laquelle se trouve le PJ sera affichée sous la forme d'une grille $6 \times 6$ où chaque élément sera représenté par un caractère (les pièges non encore identifiés n'apparaîtront pas).
La saisie d'une commande se fera simplement sous la forme `action objet` (par exemple `ramasser clé`).

L'IHM pourra par la suite être remplacée par une version utilisant un affichage graphique (__optionnel__).

### Interactions
le PJ devra pouvoir effectuer les actions suivantes :
se déplacer/sauter/franchir, ramasser, parler/soutirer/convaincre, attaquer, ouvrir/crocheter/forcer, chercher (un piège).
Les [tests de caractéristique](https://www.co-drs.org/fr/jeu/regles/les-actions/resolution-dun-test) adaptés seront utilisés pour résoudre les tentatives.
