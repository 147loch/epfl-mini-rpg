package ch.epfl.cs107.play.game.arpg.actor;

public interface PlayerForGUI {

	public float tookDamage();
	
	public float getHp();
	
	public float getMaxHp();
	
	public int getInventoryMoney();
	
	public ARPGItem getCurrentItem();
}
