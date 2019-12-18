package ch.epfl.cs107.play.game.arpg.actor.areaentity.collectable;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Heart extends CollectableAreaEntity {
	
	/**
	 * Constructor for the Heart
	 * @param area the area
	 * @param orientation the orientation
	 * @param position the position in the area
	 */
	public Heart(Area area, Orientation orientation, DiscreteCoordinates position) {
		super(area, orientation, position, "zelda/heart");
	}
	
	/**
	 * Return the amount of life of the Heart
	 * @return the amount of life of the Heart
	 */
	public float getHeartBack() {
		return 1.f;
	}
	
	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
        ((ARPGInteractionVisitor)v).interactWith(this);
	}
}
