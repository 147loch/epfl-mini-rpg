# Classes, interfaces, enums et paquetages

```
[ch]
    [epfl]
    	[cs107]
    		[play]
    			[game]
    				[actor]
    					(...)
    				[areagame]
    					[actor]
    						(...)
    					(...)
    					[handler]
    						(...)
    					[io]
    						(...)
    				[arpg]
    					[actor]
    						[areaentity]
    							Bomb
    							CastleDoor
    							CaveDoor
    							[collectable]
    								ArrowItem
    								Bow
    								CastleKey
    								Coin
    								CollectableAreaEntity
    								Heart
    								Staff
    								Sword
    							FireSpell
    							Grass
    							Rock
    							SignEntity
    							WaterFountain
    						ARPGInventory
    						ARPGInventoryGUI
    						ARPGItem
    						ARPGPlayer
    						ARPGPlayerStatusGUI
    						[battle]
    							DamageType
    							[monster]
    								DarkLord
    								FlameSkull
    								LogMonster
    								MonsterEntity
    							[weapon]
    								Arrow
    								MagicWaterProjectile
    								Projectile
    						[entity]
    							Lilypads
    							SoundEntity
    							WaterEffect
    							Waterfall
    						FlyableEntity
    						GameOver
    						[npc]
    							Emotion
    							King
    							NPC
    						[puzzle]
    							Actionable
    							ActionableStaff
    							HiddenBridge
    							Lever
    							PressurePlate
    						Rarity
    						Shop
    					[area]
    						ARPGArea
    						Chateau
    						Ferme
    						GrotteMew
    						MaisonFerme
    						Route
    						RouteChateau
    						RouteTemple
    						Temple
    						Village
    					ARPG
    					ARPGBehavior
    					[handler]
    						ARPGInteractionVisitor
    					Test
    				Game
    				[inventory]
    					Inventory
    					InventoryItem
    				[keybindings]
    					Action
    					Event
    					Key
    					KeyboardAction
    					KeyboardEventListener
    					KeyboardEventRegister
    					KeyboardKey
    					StaticKeyboardEventListener
    					XMLBinding
    					XMLBindings
    				Playable
    				[rpg]
    					[actor]
    						Dialog
    						Door
    						LightHalo
    						Player
    						RPGSprite
    						Sign
    					[handler]
    						RPGInteractionVisitor
    					RPG
    				[tutos]
    					[actor]
    						GhostPlayer
    						SimpleGhost
    					[area]
    						SimpleArea
    						[tuto1]
    							Ferme
    							Village
    						[tuto2]
    							Ferme
    							Village
    						Tuto2Area
    					Tuto1
    					Tuto2
    					Tuto2Behavior
    				Updatable
    			[io]
    				DefaultFileSystem
    				FileSystem
    				FolderFileSystem
    				ResourceFileSystem
    				XMLTexts
    				ZipFileSystem
    			[math]
    				Attachable
    				Circle
    				DiscreteCoordinates
    				Node
    				Polygon
    				Polyline
    				Positionable
    				RandomEvent
    				RandomGenerator
    				RegionOfInterest
    				Shape
    				TextAlign
    				Transform
    				Vector
    			Play
    			[recorder]
    				Record
    				[recordEntry]
    					KeyboardPressedRecordEntry
    					KeyboardReleasedRecordEntry
    					MouseButtonPressedRecordEntry
    					MouseButtonReleasedRecordEntry
    					MouseMoveRecordEntry
    					RecordEntry
    				Recorder
    				RecordReplayer
    			[signal]
    				[logic]
    					And
    					Logic
    					LogicGate
    					LogicNumber
    					MultipleAnd
    					Nand
    					Not
    					Or
    					Xor
    				Numeric
    				Signal
    				[wave]
    					Sawtooth
    					Sine
    					Square
    					Triangle
    					Waveform
    			[window]
    				Audio
    				Button
    				Canvas
    				Image
    				Keyboard
    				Mouse
    				Sound
    				[swing]
    					ImageItem
    					Item
    					ShapeItem
    					SoundItem
    					SwingImage
    					SwingSound
    					SwingWindow
    					TextItem
    				Window
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
