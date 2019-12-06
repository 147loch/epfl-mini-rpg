package ch.epfl.cs107.play.game.arpg.actor.collectable;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class HeartCollectable extends CollectableAreaEntity {
	
	public HeartCollectable(Area area, Orientation orientation, DiscreteCoordinates position) {
		super(area, orientation, position, "zelda/heart");
	}
	
	public float getHeartBack() {
		return 1.f;
	}
	
	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
        ((ARPGInteractionVisitor)v).interactWith(this);
	}
}
