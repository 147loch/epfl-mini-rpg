package ch.epfl.cs107.play.game.arpg.actor.collectable;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;

public class CastleKey extends CollectableAreaEntity {

	public CastleKey(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position, "zelda/key", 1, 16, 1.f, new Vector(0.f, 0.f));
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v) {
    	((ARPGInteractionVisitor)v).interactWith(this);
    }
}
