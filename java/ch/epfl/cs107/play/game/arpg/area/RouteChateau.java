package ch.epfl.cs107.play.game.arpg.area;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Foreground;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;

public class RouteChateau extends ARPGArea {
	
	@Override
	public String getTitle() {
		return "zelda/RouteChateau";
	}

	@Override
	protected void createArea() {
		this.registerActor(new Background(this));
		this.registerActor(new Foreground(this));
		
		this.registerActor(new Door("zelda/Ferme", new DiscreteCoordinates(4, 1),
				Logic.TRUE, this, Orientation.UP,
				new DiscreteCoordinates(4, 19), new DiscreteCoordinates(5, 19)));
		
		this.registerActor(new Door("zelda/Ferme", new DiscreteCoordinates(14, 1),
				Logic.TRUE, this, Orientation.UP,
				new DiscreteCoordinates(13, 19), new DiscreteCoordinates(14, 19),
				new DiscreteCoordinates(15, 19)));
		
		this.registerActor(new Door("zelda/Route", new DiscreteCoordinates(9, 1),
				Logic.TRUE, this, Orientation.UP,
				new DiscreteCoordinates(29, 19), new DiscreteCoordinates(30, 19)));
	}
}
