# Note importante

> **ATTENTION**: Fichiers manquants dans `res/`

Afin de soumettre notre projet, nous avons dû omettre certains fichiers que nous avons rajoutés dans `res/`, ils sont disponibles à l'addresse suivante:
https://drive.google.com/drive/folders/1DLTQZ81njx7j2XYH97izlQByb7ZLf6w6?usp=sharing

Il suffira de glisser le dossier `res/` de `0_CUSTOM-RESOURCES` dans le dossier racine de notre projet, et les sprites et sons personnalisés (et leur sources décrites dans `README.md`) seront ajoutés automatiquement.

Le drive contient aussi l'image de l'hierarchie utilisée dans notre projet. (Comme décrit dans `CONCEPTION.md`) 

# Classes, interfaces, enums et paquetages

L'énumération ci-dessous donne une idée de base quant aux choix de placements effectués.
Il est probablement plus simple de regarder le UML généré ou alors l'image qui y correspond et/ou le PDF `classmap.pdf` qui se trouvent dans le Google Drive suivant: 
https://drive.google.com/drive/folders/1DLTQZ81njx7j2XYH97izlQByb7ZLf6w6?usp=sharing

```
[ch]
    [epfl]
        [cs107]
            [play]
                [game]
                    [actor]
                        (...)
                    [areagame]
                        (...)
                    [arpg] 
                        [actor] (P) Main actor package
                            [areaentity] (P) Holds all the AreaEntities, not movable
                                [collectable] (P) Collectable AreaEntities, defined by the corresponding abstract class.
                                    ArrowItem (C)
                                    Bow (C)
                                    CastleKey (C)
                                    Coin (C)
                                    CollectableAreaEntity (A) Class with the basic definitions for collectable items
                                    Heart (C)
                                    Staff (C)
                                    Sword (C)
                                Bomb (C) 
                                CastleDoor (C)
                                CaveDoor (C)
                                FireSpell (C)
                                Grass (C)
                                Rock (C)
                                SignEntity (C)
                                WaterFountain (C)
                            [battle] (P) All actors related to battle
                                [monster] (P) The Monster MovableAreaEntities and defined by the corresponding abstract class are placed here
                                    DarkLord (C)
                                    FlameSkull (C)
                                    LogMonster (C)
                                    MonsterEntity (A) Class which holds all the definitions for monsters
                                [weapon] (P) Projectile MovableAreaEntities defined by the corresponding abstract class which are able to hurt the monsters, other weapons are simple entities placed in [areaentity]
                                    Arrow (C) This is not the collectable arrow, it is the one that is launched by the bow
                                    MagicWaterProjectile (C)
                                    Projectile (A) Definitions for projectiles
                                DamageType (E) Damage types, placed here as it is mainly used by MonsterEntity
                            [entity] (P) Simple entities that are placed in impassable zones and ignore area restriction (mainly decorations)
                                Lilypads (C)
                                SoundEntity (I) This is an interface placed here as it defines entity properties, used by Waterfall and WaterFountain
                                WaterEffect (C)
                                Waterfall (C)
                            [npc] (P) This package holds all NPC-like AreaEntities
                                Emotion (E) Corresponds to the bubble, used only by NPCs so it is placed here
                                King (C)
                                NPC (C)
                            [puzzle] (P) Holds all the puzzle-related classes, mainly properties, but some can act as signals
                                Actionable (I) Used by puzzle items
                                ActionableStaff (C) Puzzle-definition of a normal Staff AreaEntity, so that it is actionnable.
                                HiddenBridge (C)
                                Lever (C)
                                PressurePlate (C)
                            ARPGInventory (C) It is an actor with protected methods limited by definition to this package, plus it interacts closely with ARPGPlayer
                            ARPGInventoryGUI (C) Linked to the inventory and the player
                            ARPGItem (E) Used by the inventory and many sub-packages here
                            ARPGPlayer (C) Main actor of the game
                            ARPGPlayerStatusGUI (C) Closely linked to the player and main actor of the game
                            FlyableEntity (I) An interface which can be used by any actor if it should be able to fly
                            GameOver (C) A main actor related to the player, does not really make sense to create a subpackage just for it
                            Rarity (I) Element that could be used in every actor, although now it is only used by Grass
                            Shop (C) An NPC which needs a particular protected access to the inventory, so it needed to be placed here
                        [area] (P) Holds every area created for the game, definitions given by the corresponding abstract class
                            ARPGArea (A) Definitions for all of the areas of this package
                            Chateau (C)
                            Ferme (C)
                            GrotteMew (C)
                            MaisonFerme (C)
                            Route (C)
                            RouteChateau (C)
                            RouteTemple (C)
                            Temple (C)
                            Village (C)
                        [handler] (P) Holds the interaction handlers with all of the Interactables, as instructed
                            ARPGInteractionVisitor (I)
                        ARPG (C) Main ARPG class
                        ARPGBehavior (C) Class linked to ARPG, placed here as instructed
                        Test (I) As instructed
                    [inventory] (P) As instructed, holds high-level inventory definitions
                        Inventory (A)
                        InventoryItem (I)
                    [keybindings] (P) Custom package, holds definitions for keyboard-related classes: Event register and KeyboardActions
                        Action (I) Definitions for KeyboardActions
                        Event (C)
                        Key (I) Definitions for KeyboardKeys
                        KeyboardAction (E)
                        KeyboardEventListener (I) Used by the register
                        KeyboardEventRegister (C)
                        KeyboardKey (E)
                        StaticKeyboardEventListener (I) Linked to KeyboardEventListener
                        XMLBinding (C) Used by XMLBindings
                        XMLBindings (FC) Class which is only used by the KeyboardAction to save the state of keybinds in an XML
                    [rpg]
                        (...)
                    (...)
                [io]
                    (...)
                [math]
                    (...)
                [recorder]
                    (...)
                [signal]
                    (...)
                [window]
                    (...)
                Play (C, main)
```

# Modifications personelles par rapport à l'énoncé
## Système de keybindings

Lors de la programmation du projet, nous avons trouvé que de coder "en dur" les touches utilisées pour
activer certaines interactions et gérer le mouvement n'était pas forcément une très bonne idée quant à la modification
possible dans le futur desdites touches.

Nous décrivons dans la section suivante le système complet. Mais c'est une modification qui fait que notre code n'est pas du
tout similaire à celui demandé. Ce sytême ne diverge cependant pas trop de l'esprit de ce qui est demandé, il ajoute juste quelques fonctionnalités supplémentaires.  

## Touche de triche pour l'apparition de bombes

Dans les instructions, il est demandé que la touche pour faire appaître des bombes n'est activable que dans `RouteChateau`.
Étant donné que la zone du village nécessite une bombe pour ouvrir la porte vers la Grotte, nous avons décidé que cette touche de
triche uniquement pouvait être utilisé dans toutes les zones. Elle est donc liée au joueur et non à la zone `RouteChateau`.

De manière générale, les touches de triches ne sont activées seulement quand le mode de test est activé dans l'interface `Test.MODE`.

## Système de rareté pour l'apparition d'objets

Nous avons effectué une petite déviation par rapport aux instructions lors de la création des constantes de `DROP` pour `Grass`.
La rareté est définie depuis des constantes dans une interface `Rarity`, donc même si les constantes demandées sont présentes dans la classe `Grass`, elles
tirent leur valeurs depuis l'interface.

## Changements dans l'affichage de l'objet en utilisation

Dans les instructions, il est demandé de simplement faire tourner tous les objets dans l'indicateur en haut à droite de l'interface du joueur.
Étant donné que les flèches sont un élément qui ne peut pas être utilisé par le joueur en tant qu'interaction (pas comme la bombe, par exemple), nous
avons décidé qu'il n'y avait pas besoin de les afficher. Elle n'apparaîtront donc que dans l'inventaire et pas dans l'affichage de l'interface.

# Extensions ajoutées
## Système de keybindings

Comme mentionné ci-dessus, nous avons implémenté un système non indiqué dans les bonus mais que nous avons trouvé particulièrement intéressant pour la
modularisation de ce projet.

Le UML de ce projet n'étant pas très clair sur le fonctionnement de ce système, nous allons redéfinir en partie le fonctionnement ci-dessous.

### Fonctionnement général

Le but de ce système est donc d'avoir une sauvegarde de toutes les "actions" du jeu liées à des touches du clavier re-définies dans un nouvel enum.

En plus du système de sauvegarde de la configuration dans un fichier XML, nous avons implémenté un système supplémentaire pour des "événements de clavier".
En bref, ce système permet de définir de façon modulable des listeners dans une classe qui seront activés à distance par le register principal, dans lequel les événements ont été enregistrés.

Ce système permet donc d'avoir toutes les touches enregistrées dans un fichier XML généré lorsque le jeu est lancé pour la première fois en utilisant les valeurs par défaut, modifiable à volonté après coup.
Si le fichier XML existe, alors les valeurs de ce fichier seront utilisées, et les actions qui s'y trouvent permettent de juste lier l'action à un bouton tel que défini par la maquette ou à créer un Event listener
qui vérifiera le bouton à chaque update et actionnera une méthode (entre autres un callback) si le bouton a été pressé (ou est maintenu pressé). 

### Fonctionnement détaillé

#### Sauvegarde des actions

##### Systèmes "d'actions"

La moitié du système est la définition des actions comme, par exemple, `MOVE_UP`. Ceci ce passe dans l'enum `KeyboardAction` qui implémente l'interface `Action`.
Chaque action est caractérisée par une touche par défaut et un titre (qui pourrait être utilisé dans un système de réglages).

L'enum assigne à sa construction la touche, en fonction de la valeur dans le fichier XML, ou, le cas échéant, la touche par défaut. Le système XML est défini plus bas.

L'enum fait référence à des touches du clavier, définies dans l'enum KeyboardKey.

##### Les touches du clavier

Nous avons redéfini l'interace `Keyboard` de la maquette dans un enum car nous avions besoin d'avoir les touches en partant d'un nom (celui qui est sauvegardé dans le fichier XML).
Cet enum implémente l'interface `Key` pour lier l'interface de la maquette avec notre enum à travers une méthode appelée `getCode()`.
  
##### Le système de sauvegarde XML

Partiellement inspiré de la classe `XMLTexts`, nous avons créé une classe `XMLBindings` qui s'occupe de la lecture, création et modification du fichier `keybindings.xml`.

Un élément du fichier est converti en un objet de type `XMLBinding`, qui a deux attributs: ceux provenant du XML.

Le fonctionnement de `XMLBindings` est décrit par commentaires dans le code, mais c'est principalement plusieurs Factories qui modifient le fichier et
le traite en quelque chose qui peut être lu par Java. 

#### Événements du clavier

##### Le register

Le système d'événements du clavier est principalement géré par la classe `KeyboardEventRegister`.
Il y a deux types de listeners d'événements: des statiques (qui n'ont qu'une seule touche et n'y dépendent pas) et les dynamiques (qui peuvent être assimilés à plusieurs touches et dont le callback va dépendre de l'action qui a déclenché l'événement)

Il y a alors beaucoup de méthodes surchargées pour traiter tous les cas possibles en fonction des interfaces utilisées pour déclarer les listener. Il est aussi possible de manuellement créer les événements mais, la plupart du temps, il est plus simple
de juste ajouter des listeners et la méthode se chargera de créer l'évenement.

Ces événements sont définis par la classe `Event` qui tient toute les propriétés nécessaire pour la bonne execution de la suite du système.

##### Listener dynamique

Le listener général, défini par la classe `KeyboardEventListener` est considéré comme dynamique, il donne la définition des méthodes qui doivent être définies pour être ensuite
donné au register. Cette interface doit être implémentée par une sous-classe dans l'acteur qui doit réagir à l'événement. Cela permet alors au register d'ensuite appeler les méthodes
sans devoir accéder à des attributs qui casseraient l'encapsulation.

##### Listener statique

Une extension de la version dynamique mais qui n'oblige que que la méthode `onKeyEvent()` soit définie.
Fonctionnne de la même manière par la suite.

##### Exécution

Une fois le register instantiée dans la classe qui possède aussi les implémentations statiques ou dynamiques des listener, et lorsque les
événements sont bien enregistrés. La classe "parent" doit ajouter à sa méthode update un appel vers la méthode `update` du register qui ne prend
aucun argument mais va vérifier si les touches de tous les événements ont été pressées ou maintenues (selon la définition donnée lors de l'ajout du listener/événement)
et appeler les méthods des listeners correspondants.



## Système d'inventaire avec interface et marchand

Nous avons implémenté un système d'inventaire complet avec séléction des objets dans une interface qui peut être créée par 
tout acteur ayant un inventaire (le player tout comme le marchand).

Le marchand est donc un simple personnage immobile qui possède un inventaire et a quelques méthodes supplémentaires pour que le joueur
puisse intéragir avec lui.

L'inventaire peut être ouvert et fermé avec la touche `I`, ou alors celle qui se trouve dans le fichier XML.
Le shop est ouvert par intéraction mais est fermé avec la même touche que pour l'inventaire.

## PNJ et Roi avec système de dialogue

Afin de réaliser un petit scénario, nous avons ajoutés des PNJ (`NPC`) dans certaines aires.

Pour tous les PNJ, nous avons créé un systèmes de bulles pour indiquer si un dialogue n'a pas encore été lu (en utilisant la touche d'interaction à distance).

## Entités animées (pour certaines) ajoutées

Nous avons créé les entités statiques suivantes:
- Nénuphars (dans la zone Route)
- Panneaux (dans plusieurs zones, possèdent tous un dialogue)
- Porte vers la grotte (au Nord du Village, ayant deux états, ouverte avec une bombe)
- Rocher avec l'épée (au Nord du Village, en haut de la colline, l'épée peut être récupérée avec la touche d'interaction à distance)
- Rochers (dispersés dans plusieurs zones)

Et d'autres entités animées:
- Cascade (en bas à droite de la zone Route, animée et émet un son en boucle)
- Effets d'eau (dans la zone village, animés)
- Fontaine (dans la zone village, animée et émet un son)

## Système d'actionnements pour créer des énigmes

- Interface `Actionable`

- Pont caché
- Baton magique activable
- Levier
- Plaque de pression

## Bruitages et interfaces gérant des entités à sons répétés

- Fontaine
- Cascade
- Activation du éevier
- Activation de la plaque de pression
- Coup sur le joueur
- Coup sur les ennemis
- Mort des ennemis
- Mort du joueur
- Transactions avec le marchand

## Game Over

## Zones ajoutées

- Temple
- Route du temple
- Grotte
- Maison

## Un petit scénario
