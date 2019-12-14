package ch.epfl.cs107.play.game.arpg.actor;

public interface PlayerForGUI {

	float tookDamage();
	
	float getHp();
	
	float getMaxHp();
	
	int getInventoryMoney();
	
	ARPGItem getCurrentItem();

}
