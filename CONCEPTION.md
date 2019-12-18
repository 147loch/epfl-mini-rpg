# Classes, interfaces, enums et paquetages

L'énumération ci-dessous donne une idée de base quant aux choix de placements effectués.
Il est probablement plus simple de regarder le UML généré ou alors l'image qui y correspond et/ou le PDF `classmap.pdf` qui se trouvent dans le Google Drive suivant: https://drive.google.com/drive/folders/1DLTQZ81njx7j2XYH97izlQByb7ZLf6w6?usp=sharing

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

## Touche de triche pour l'apparition de bombes

## Système de rareté pour l'apparition d'objets

## Changements dans l'affichage de l'objet en utilisation

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
