# Note importante

> **ATTENTION**: Fichiers manquants dans `res/`

Afin de soumettre notre projet, nous avons dû omettre certains fichiers que nous avons rajoutés dans `res/`, ils sont disponibles à l'addresse suivante:
https://drive.google.com/drive/folders/1DLTQZ81njx7j2XYH97izlQByb7ZLf6w6?usp=sharing

Il suffira de glisser le dossier `res/` de `0_CUSTOM-RESOURCES` dans le dossier racine de notre projet, et les sprites et sons personnalisés (et leur sources décrites dans `CONCEPTION.md`) seront ajoutés automatiquement.

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
avons décidé qu'il n'y avait pas besoin de les afficher 

# Extensions ajoutées
## Système de keybindings

## Système d'inventaire avec interface et marchand

## PNJ et Roi avec système de dialogue

- Système de bulles pour indiquer un dialogue non lu

## Entités animées (pour certaines) ajoutées

- Nénuphars
- Cascade
- Effets d'eau
- Rochers
- Fontaine
- Panneaux
- Porte vers la grotte
- Rocher avec l'épée

## Système d'actionnements pour créer des énigmes

- Pont caché
- Baton magique activable
- Levier
- Plaque de pression
- Interface `Actionable`

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
