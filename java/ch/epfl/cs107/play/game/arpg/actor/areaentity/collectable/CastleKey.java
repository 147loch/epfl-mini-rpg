package ch.epfl.cs107.play.game.arpg.actor.areaentity.collectable;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class CastleKey extends CollectableAreaEntity {

	/**
	 * Constructor for the CastleKey
	 * @param area the area
	 * @param orientation the orientation
	 * @param position the position in the area
	 */
	public CastleKey(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position, "zelda/key", 1, 16);
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v) {
    	((ARPGInteractionVisitor)v).interactWith(this);
    }
}
