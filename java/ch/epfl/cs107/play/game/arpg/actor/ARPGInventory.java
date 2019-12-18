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

        for (InventoryItem key : super.getMap().keySet()) {
            fortune += key.getPrice() * super.getMap().get(key);
        }
	}
	
	protected void addMoney(int money) {
		this.money += money;
		this.fortune += money;
	}

	private int getItemAmount(InventoryItem item) { return super.getMap().get(item); }
	
	public int getMoney() {
		return money;
	}
	public int getFortune() {
		return fortune;
	}
	public int getAmountOf(InventoryItem item) { return super.getMap().containsKey(item) ? getItemAmount(item) : 0; }

	// Overrides the basic inventory methods so that we include the fortune management system
    // This way the following two methods are properly encapsulated and only available to actors.

	@Override
	protected boolean addEntry(InventoryItem item, int count) {
		this.fortune += item.getPrice() * count;
	    return super.addEntry(item, count);
	}

	@Override
	protected boolean removeEntry(InventoryItem item, int count) {
        this.fortune -= item.getPrice() * count;
	    return super.removeEntry(item, count);
	}

	protected List<InventoryItem> getItemList() {
		return new ArrayList<>(super.getMap().keySet());
	}


}
