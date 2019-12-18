package ch.epfl.cs107.play.game.arpg.actor.areaentity.collectable;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class ArrowItem extends CollectableAreaEntity {

	/**
	 * Constructor for the ArrowItem
	 * @param area the area
	 * @param orientation the orientation
	 * @param position the position in the area
	 */
	public ArrowItem(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position, "zelda/arrow.icon", 1, 16);
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v) {
    	((ARPGInteractionVisitor)v).interactWith(this);
    }
}
