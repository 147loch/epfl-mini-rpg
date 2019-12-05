package ch.epfl.cs107.play.game.arpg.actor;

import ch.epfl.cs107.play.game.inventory.Inventory;
import ch.epfl.cs107.play.game.inventory.InventoryItem;

import java.util.ArrayList;
import java.util.List;

public class ARPGInventory extends Inventory {
	private int money;
	private int fortune;
	
	public ARPGInventory(int money) {
		this.money = money;
		fortune = this.money;
	}
	
	protected void addMoney(int money) {
		this.money += money;
		this.fortune += money;
	}
	
	public int getMoney() {
		return money;
	}
	
	public int getFortune() {
		fortune = money;
		for (InventoryItem key : super.getMap().keySet()) {
			fortune += key.getPrice();
		}
		return fortune;
	}

	@Override
	protected boolean addEntry(InventoryItem item, int count) {
		return super.addEntry(item, count);
	}

	@Override
	protected boolean removeEntry(InventoryItem item, int count) {
		return super.removeEntry(item, count);
	}

	protected List<InventoryItem> getItemList() {
		return new ArrayList<>(getMap().keySet());
	}
}
