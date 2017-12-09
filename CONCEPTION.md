# BikeGame

## Intégration avec l'architecture fournie

Les classes pouvant être intégrées dans différents types de jeux font partie du package *ch.epfl.cs107.play.game.actor.general*.

Les classes spécifiques au BikeGame se trouvent dans *ch.epfl.cs107.play.game.actor.bike*.

Les niveaux du BikeGame sont dans *ch.epfl.cs107.play.game.actor.bike.levels*.

## Modification de l'architecture suggérée

Le BikeGame, qui hérite de ActorGame, a une autorité totale sur la logique du jeu. Au lieu d'être notifié par Bike (ou Finish) que le joueur a gagné (ou perdu), le jeu vérifie lui-même l'état du Bike et du Finish à chaque update, et décide de l'issue de la partie.

Certains éléments importants du jeu sont référencés par des attributs, et non par le biais de setPayload() et getPayload().
Chaque type de jeu a des éléments principaux de types différents (ex. Bike dans BikeGame, Character dans PlatformerGame)

La "caméra" contenue dans ActorGame peut être secouée grâce à ActorGame.setViewShake(float)

# Classes

#### *general*
*ch.epfl.cs107.play.game.actor.general*

- **AnchoredObject** : Il s'agit d'un DynamicObject pouvant être accroché en un point.
- **Crate** : C'est un DynamicObject en forme de carré.
- **Decoration** : Permet d'ajouter des indications visuelles ou des dessins dans le monde du jeu.
- **DynamicObject** : Un GameEntity non fixé avec un Polygon comme représentation physique.
- **SlippyTerrain** : Un terrain avec une friction réduite.
- **Stickman** : Un bonhomme en fil de fer avec des setters pour les coordonnées de chaque membre.
- **Terrain** : Un GameEntity fixé avec un Polyline comme représentation physique.
- **Text** : Permet d'ajouter du texte fixe dans le monde du jeu.
- **Trampoline** : Un Terrain avec une restitution élevée.
- **Trigger** : Un GameEntity avec un BasicContactListener accessible dans ses sous-classes, permettant de réagir aux collisions.
- **Wheel** : Une roue pouvant être attachée à un Entity et contrôlée.

#### *bike*
*ch.epfl.cs107.play.game.actor.bike*

- **Bike** : Un GameEntity représentant un vélo avec deux Wheel et un Driver. C'est l'acteur principal de BikeGame.
- **BikeGame** : Un Game contenant toute la logique de jeu spécifique à un "Bike Game".
- **Driver** : Un Stickman associé à un vélo.
- **Finish** : Un Trigger spécifique au BikeGame, dont la représentation graphique est un drapeau.

#### *bike.levels*
*ch.epfl.cs107.play.game.actor.bike.levels*

- **SimpleBikeLevel** : Un Level de test, simplement pour tester l'architecture proposée.
- **XmlBikeLevel** : Un Level dont les acteurs sont créés à l'aide d'un XmlBikeLevelLoader.
- **XmlBikeLevelLoader** : Permet de charger un niveau du BikeGame au format XML, créé avec Tiled (.tmx).

## Ajout de nouveaux niveaux

Pour créer un nouveau niveau, dans l'éditeur Tiled, commencer par créer une nouvelle map avec les dimensions souhaitées, et la dimension des tiles à 1x1.

<img src="https://img15.hostingpics.net/pics/562442ScreenShot20171209at185115.png" width="200"/>

Pour qu'un niveau soit valide, il est nécessaire de définir un point de départ pour Bike, ainsi qu'un Finish (voir [Création d'un nouvel objet](#création-dun-nouvel-objet)).
Le Finish est en contact avec le sol, tandis que le Bike sera de préférence un carreau au-dessus, pour laisser une marge de manoeuvre au moteur physique.

<img src="https://img15.hostingpics.net/pics/120731ScreenShot20171209at191516.png" width="200"/>

### Création d'un nouvel objet
Pour créer un nouvel objet, sélectionner l'outil adapté :
- Rectangle
    - Bike
    - Finish
    - Crate
- Polygon
    - AnchoredObject
    - DynamicObject
- Polyline
    - Decoration
    - SlippyTerrain
    - Terrain
    - Trampoline
- Text
    - Text

On crée ensuite l'objet, puis on explicite son Type.

<img src="https://img15.hostingpics.net/pics/244751ScreenShot20171209at193242.png" width="200"/>

*Pour AnchoredObject, il est nécessaire de spécifier les attributs (float) Anchor1X, Anchor1Y, Anchor2X, Anchor2Y*

<img src="https://img15.hostingpics.net/pics/213138ScreenShot20171209at193447.png" width="200"/>