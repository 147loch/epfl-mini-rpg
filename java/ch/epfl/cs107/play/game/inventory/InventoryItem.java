package ch.epfl.cs107.play.game.inventory;

public class InventoryItem {
	
	private String name;
	private float weight;
	private int price;
	
	public InventoryItem(String name, float weight, int price) {
		this.name = name;
		this.weight = weight;
		this.price = price;
	}
	
	protected String getName() {
		return name;
	}
	
	protected float getWeight() {
		return weight;
	}
}
