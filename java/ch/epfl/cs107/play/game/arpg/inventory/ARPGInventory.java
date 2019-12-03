package ch.epfl.cs107.play.game.arpg.inventory;

import ch.epfl.cs107.play.game.inventory.Inventory;

public class ARPGInventory extends Inventory {
	private int money;
	private int fortune;
	
	public ARPGInventory(int money) {
		this.money = money;
		fortune = this.money;
	}
	
	protected void addMoney(int money) {
		this.money += money;
	}
	
	public int getMoney() {
		return money;
	}
	
	public int getFortune() {
		return fortune;
	}
}
