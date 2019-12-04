package ch.epfl.cs107.play.game.arpg.inventory;

import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.inventory.InventoryItem;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.Positionable;
import ch.epfl.cs107.play.math.Transform;

public enum ARPGItem implements InventoryItem {
	ARROW("zelda/arrow.icon") {
		@Override
		public String getName() {
			return "Arrow";
		}
	},
	SWORD("zelda/arrow.icon") {
		@Override
		public String getName() {
			return "Sword";
		}
	};

	ARPGItem(String iconId) {

	}
}
