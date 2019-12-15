package ch.epfl.cs107.play.game.arpg.actor;

import ch.epfl.cs107.play.game.inventory.InventoryItem;

public interface PlayerForGUI {

	float tookDamage();
	
	float getHp();
	
	float getMaxHp();
	
	int getInventoryMoney();
	
	ARPGItem getCurrentItem();

	int getAmountOf(InventoryItem item);
}
