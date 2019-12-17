package ch.epfl.cs107.play.game.arpg.handler;

import ch.epfl.cs107.play.game.arpg.ARPGBehavior.ARPGCell;
import ch.epfl.cs107.play.game.arpg.actor.ARPGPlayer;
import ch.epfl.cs107.play.game.arpg.actor.battle.monster.MonsterEntity;
import ch.epfl.cs107.play.game.arpg.actor.battle.weapon.Arrow;
import ch.epfl.cs107.play.game.arpg.actor.battle.weapon.MagicWaterProjectile;
import ch.epfl.cs107.play.game.arpg.actor.collectable.ArrowItem;
import ch.epfl.cs107.play.game.arpg.actor.collectable.Bow;
import ch.epfl.cs107.play.game.arpg.actor.collectable.CastleKey;
import ch.epfl.cs107.play.game.arpg.actor.collectable.Coin;
import ch.epfl.cs107.play.game.arpg.actor.collectable.Heart;
import ch.epfl.cs107.play.game.arpg.actor.collectable.Staff;
import ch.epfl.cs107.play.game.arpg.actor.collectable.Sword;
import ch.epfl.cs107.play.game.arpg.actor.entity.Bomb;
import ch.epfl.cs107.play.game.arpg.actor.entity.CastleDoor;
import ch.epfl.cs107.play.game.arpg.actor.entity.FireSpell;
import ch.epfl.cs107.play.game.arpg.actor.entity.Grass;
import ch.epfl.cs107.play.game.arpg.actor.puzzle.Target;
import ch.epfl.cs107.play.game.arpg.actor.sign.King;
import ch.epfl.cs107.play.game.arpg.actor.sign.Npc;
import ch.epfl.cs107.play.game.rpg.actor.Sign;
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
	
	default void interactWith(Bow bow) {}

	default void interactWith(Staff staff) {}
	
	default void interactWith(MonsterEntity monster) {}
	
	default void interactWith(Arrow arrow) {}
	
	default void interactWith(ArrowItem arrow) {}
	
	default void interactWith(MagicWaterProjectile projectile) {}
	
	default void interactWith(Target activator) {}
	
	default void interactWith(Sword sword) {}

	default void interactWith(FireSpell fireSpell) {}

	default void interactWith(Sign sign) {}
	
	default void interactWith(King king) {}
	
	default void interactWith(Npc pnj) {}
}
