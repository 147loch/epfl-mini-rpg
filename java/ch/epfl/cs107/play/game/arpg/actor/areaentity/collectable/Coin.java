package ch.epfl.cs107.play.game.arpg.actor.areaentity.collectable;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Coin extends CollectableAreaEntity {
	
	/**
	 * Constructor for the Coin
	 * @param area the area
	 * @param orientation the orientation
	 * @param position the position in the area
	 */
	public Coin(Area area, Orientation orientation, DiscreteCoordinates position) {
		super(area, orientation, position, "zelda/coin");
	}
	
	/**
	 * Return the amount of money of the Coin
	 * @return the amount of money of the Coin
	 */
	public int getMoneyBack() {
		return 50;
	}
	
	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
        ((ARPGInteractionVisitor)v).interactWith(this);
    }
}
