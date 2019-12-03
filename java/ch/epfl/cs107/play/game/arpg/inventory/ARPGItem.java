package ch.epfl.cs107.play.game.arpg.inventory;

import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.inventory.InventoryItem;
import ch.epfl.cs107.play.math.Positionable;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;

public class ARPGItem extends InventoryItem implements Positionable {

	public final static ARPGItem Arrow = new ARPGItem("Arrow", 0.f, 1, "zelda/arrow.icon");
	
	private Sprite sprite;
	
	public ARPGItem(String name, float weight, int price, String spriteString) {
		super(name, weight, price);
		
		sprite = new Sprite(spriteString, 1.f, 1.f, this);
	}

	@Override
	public Transform getTransform() {
		return null;
	}

	@Override
	public Vector getVelocity() {
		return null;
	}
}
