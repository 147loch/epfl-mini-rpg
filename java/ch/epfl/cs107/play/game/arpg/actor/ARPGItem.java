package ch.epfl.cs107.play.game.arpg.actor;

import ch.epfl.cs107.play.game.inventory.InventoryItem;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.Positionable;

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
	},
	STAFF("zelda/staff_water.icon") {
		@Override
		public String getName() {
			return "Staff";
		}
	},
	BOW("zelda/bow.icon") {
		@Override
		public String getName() {
			return "Bow";
		}
	},
	BOMB("zelda/bomb") {
		@Override
		public String getName() {
			return "Bomb";
		}
	},
	CASTLE_KEY("zelda/key") {
		@Override
		public String getName() {
			return "Castle Key";
		}
	};

	private String iconId;

	ARPGItem(String iconId) {
		this.iconId = iconId;
	}

	public RPGSprite getSprite(Positionable self) {
		return new RPGSprite(this.iconId, 1.f, 1.f, self);
	}
}
