package ch.epfl.cs107.play.game.arpg.inventory;

import ch.epfl.cs107.play.game.inventory.Inventory;
import ch.epfl.cs107.play.game.inventory.InventoryItem;

public class ARPGInventory extends Inventory {
	private int money;
	private int fortune;
	
	public ARPGInventory(int money) {
		this.money = money;
		fortune = this.money;
		for (InventoryItem key : super.getMap().keySet()) {
			fortune += key.getPrice();
		}
	}
	
	protected void addMoney(int money) {
		this.money += money;
		this.fortune += fortune;
	}
	
	public int getMoney() {
		return money;
	}
	
	public int getFortune() {
		return fortune;
	}
}
