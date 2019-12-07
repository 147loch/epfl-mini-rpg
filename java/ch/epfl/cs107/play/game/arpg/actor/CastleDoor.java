package ch.epfl.cs107.play.game.arpg.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;

public class CastleDoor extends Door {

	public CastleDoor(String destination, DiscreteCoordinates otherSideCoordinates, Area area,
			Orientation orientation, DiscreteCoordinates position, DiscreteCoordinates secondPosition) {
		super(destination, otherSideCoordinates, Logic.FALSE, area, orientation, position, secondPosition);
	}
	
	public void changeSignal() {
		
	}
}
