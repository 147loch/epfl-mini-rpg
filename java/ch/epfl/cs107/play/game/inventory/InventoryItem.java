package ch.epfl.cs107.play.game.inventory;


// TODO extends positionable
public interface InventoryItem {

	/**
	 * @return Item name
	 */
	String getName();

	/**
	 * @return Item weight
	 */
	default float getWeight() { return 0.f; };

	/**
	 * @return the price of the item
	 */
	default int getPrice() { return 0; };

}
