package ch.epfl.cs107.play.game.arpg.handler;

import ch.epfl.cs107.play.game.arpg.ARPGBehavior.ARPGCell;
import ch.epfl.cs107.play.game.arpg.actor.ARPGPlayer;
import ch.epfl.cs107.play.game.arpg.actor.Bomb;
import ch.epfl.cs107.play.game.arpg.actor.CastleDoor;
import ch.epfl.cs107.play.game.arpg.actor.collectable.ArrowItem;
import ch.epfl.cs107.play.game.arpg.actor.collectable.Bow;
import ch.epfl.cs107.play.game.arpg.actor.collectable.CastleKey;
import ch.epfl.cs107.play.game.arpg.actor.collectable.Coin;
import ch.epfl.cs107.play.game.arpg.actor.collectable.Heart;
import ch.epfl.cs107.play.game.arpg.actor.collectable.Staff;
import ch.epfl.cs107.play.game.arpg.actor.collectable.Sword;
import ch.epfl.cs107.play.game.arpg.actor.entity.Grass;
import ch.epfl.cs107.play.game.arpg.actor.battle.monster.FlameSkull;
import ch.epfl.cs107.play.game.arpg.actor.battle.monster.LogMonster;
import ch.epfl.cs107.play.game.arpg.actor.battle.monster.MonsterEntity;
import ch.epfl.cs107.play.game.arpg.actor.battle.weapon.Arrow;
import ch.epfl.cs107.play.game.arpg.actor.battle.weapon.MagicWaterProjectile;
import ch.epfl.cs107.play.game.arpg.actor.puzzle.Activator;
import ch.epfl.cs107.play.game.rpg.handler.RPGInteractionVisitor;

public interface ARPGInteractionVisitor extends RPGInteractionVisitor {

	default void interactWith(ARPGCell cell) {}
	
	default void interactWith(ARPGPlayer player) {}
	
	default void interactWith(Grass grass) {}

	default void interactWith(Bomb bomb) {}
	
	default void interactWith(Coin coin) {}
	
	default void interactWith(Heart heart) {}
	
	default void interactWith(CastleKey key) {}

	default void interactWith(CastleDoor castleDoor) {}

	default void interactWith(FlameSkull skull) {}

	default void interactWith(LogMonster logMonster) {}
	
	default void interactWith(Bow bow) {}

	default void interactWith(Staff staff) {}
	
	default void interactWith(MonsterEntity monster) {}
	
	default void interactWith(Arrow arrow) {}
	
	default void interactWith(ArrowItem arrow) {}
	
	default void interactWith(MagicWaterProjectile projectile) {}
	
	default void interactWith(Activator activator) {}
	
	default void interactWith(Sword sword) {}
}
