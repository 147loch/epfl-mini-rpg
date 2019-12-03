package ch.epfl.cs107.play.game.inventory;

import java.util.HashMap;
import java.util.Map;

public class Inventory {

	private float maxWeight;
	private float weight;
	private Map<InventoryItem, Integer> inventory;
	
	protected Inventory() {
		inventory = new HashMap<>();
		maxWeight = 20.f;
		weight = 0;
	}
	
	public boolean isInInventory(InventoryItem item) {
		for (InventoryItem key : inventory.keySet()) {
			if (key.getName().equals(item.getName())) {
				return true;
			}
		}
		return false;
	}
	
	protected boolean addEntry(InventoryItem item, int count) {
		for (InventoryItem key : inventory.keySet()) {
			if (key.getName().equals(item.getName())) {
				if (item.getWeight() * count <= (maxWeight - weight)) {
					inventory.replace(key, inventory.get(key) + count);
					weight += item.getWeight() * count;
					return true;
				}
			}
		}
		return false;
	}
	
	protected boolean removeEntry(InventoryItem item, int count) {
		if (item.getWeight() * count > weight)
			return false;
		for (InventoryItem key : inventory.keySet()) {
			if (key.getName().equals(item.getName())) {
					inventory.replace(key, inventory.get(key) - count);
					weight -= item.getWeight() * count;
					return true;
			}
		}
		return false;
	}
}
