package ch.epfl.cs107.play.game.arpg.actor.areaentity.collectable;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;

public class Staff extends CollectableAreaEntity {

	/**
	 * Constructor for the Staff
	 * @param area the area
	 * @param orientation the position
	 * @param position the position in the area
	 */
    public Staff(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position, "zelda/staff", 8, 32, 2.f, new Vector(-0.5f, 0.f), 3);
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v) {
        ((ARPGInteractionVisitor) v).interactWith(this);
    }

}
