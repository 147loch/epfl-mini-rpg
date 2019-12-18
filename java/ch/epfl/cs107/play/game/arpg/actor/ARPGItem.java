package ch.epfl.cs107.play.game.arpg.actor;

import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.game.inventory.InventoryItem;

public enum ARPGItem implements InventoryItem {
	ARROW      ("zelda/arrow.icon")       {
		@Override public String getName()  { return "Arrow";      }
		@Override public int    getPrice() { return 10;           }
	},
	SWORD      ("zelda/sword.icon")       {
		@Override public String getName()  { return "Sword";      }
	},
	STAFF      ("zelda/staff_water.icon") {
		@Override public String getName()  { return "Staff";      }
	},
	BOW        ("zelda/bow.icon")         {
		@Override public String getName()  { return "Bow";        }
		@Override public int    getPrice() { return 100;          }
	},
	BOMB       ("zelda/bomb")             {
		@Override public String getName()  { return "Bomb";       }
		@Override public int    getPrice() { return 20;           }
	},
	CASTLE_KEY ("zelda/key") 			 {
		@Override public String getName()  { return "Castle Key"; }
	};

	private String iconId;

	/**
	 * Constructor for the ARPGItem
	 * @param iconId the string path for the sprite
	 */
	ARPGItem(String iconId) {
		this.iconId = iconId;
	}

	/**
	 * Return the ressource path
	 * @return the ressource path
	 */
	public String getResourcePath() {
		return ResourcePath.getSprite(this.iconId);
	}
}
