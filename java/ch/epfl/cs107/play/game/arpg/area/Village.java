package ch.epfl.cs107.play.game.arpg.area;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Foreground;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.arpg.actor.collectable.Sword;
import ch.epfl.cs107.play.game.arpg.actor.puzzle.CaveDoor;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;

public class Village extends ARPGArea {

	protected static CaveDoor caveDoor;

	public Village() {
		super();
		caveDoor = new CaveDoor("GrotteMew", new DiscreteCoordinates(8, 3),
				this, Orientation.UP, new DiscreteCoordinates(25, 18));
	}

	@Override
	public String getTitle() {
		return "zelda/Village";
	}

	@Override
	protected void createArea() {
		registerActor(new Background(this));
		registerActor(new Foreground(this));
		
		registerActor(new Door("zelda/Ferme", new DiscreteCoordinates(4, 1),
				Logic.TRUE, this, Orientation.UP,
				new DiscreteCoordinates(4, 19), new DiscreteCoordinates(5, 19)));
		
		registerActor(new Door("zelda/Ferme", new DiscreteCoordinates(14, 1),
				Logic.TRUE, this, Orientation.UP,
				new DiscreteCoordinates(13, 19), new DiscreteCoordinates(14, 19),
				new DiscreteCoordinates(15, 19)));
		
		registerActor(new Door("zelda/Route", new DiscreteCoordinates(9, 1),
				Logic.TRUE, this, Orientation.UP,
				new DiscreteCoordinates(29, 19), new DiscreteCoordinates(30, 19)));

		registerActor(caveDoor);

		registerActor(new Sword(this, Orientation.UP, new DiscreteCoordinates(13, 18)));
	}
}
