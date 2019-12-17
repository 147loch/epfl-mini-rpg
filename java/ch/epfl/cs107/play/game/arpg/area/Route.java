package ch.epfl.cs107.play.game.arpg.area;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Foreground;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.arpg.actor.areaentity.Grass;
import ch.epfl.cs107.play.game.arpg.actor.areaentity.Rock;
import ch.epfl.cs107.play.game.arpg.actor.areaentity.SignEntity;
import ch.epfl.cs107.play.game.arpg.actor.battle.monster.LogMonster;
import ch.epfl.cs107.play.game.arpg.actor.entity.Lilypads;
import ch.epfl.cs107.play.game.arpg.actor.entity.Waterfall;
import ch.epfl.cs107.play.game.arpg.actor.puzzle.HiddenBridge;
import ch.epfl.cs107.play.game.arpg.actor.puzzle.Lever;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.signal.logic.Logic;

public class Route extends ARPGArea {

	private Waterfall waterfall = new Waterfall(new Vector(15, 3));

	@Override
	public String getTitle() {
		return "zelda/Route";
	}

	@Override
	protected void createArea() {
		registerActor(new Background(this));
		registerActor(new Foreground(this));
		
		registerActor(new Door("zelda/Ferme", new DiscreteCoordinates(18, 15),
				Logic.TRUE, this, Orientation.LEFT,
				new DiscreteCoordinates(0, 15), new DiscreteCoordinates(0, 16)));
		
		registerActor(new Door("zelda/Village", new DiscreteCoordinates(29, 18),
				Logic.TRUE, this, Orientation.DOWN,
				new DiscreteCoordinates(9, 0), new DiscreteCoordinates(10, 0)));

		registerActor(new Door("zelda/RouteChateau", new DiscreteCoordinates(9, 1),
				Logic.TRUE, this, Orientation.UP,
				new DiscreteCoordinates(9, 19), new DiscreteCoordinates(10, 19)));

		registerActor(new Door("zelda/RouteTemple", new DiscreteCoordinates(1, 5),
				Logic.TRUE, this, Orientation.RIGHT,
				new DiscreteCoordinates(19, 9), new DiscreteCoordinates(19, 10), new DiscreteCoordinates(19, 11)));
		
		for (int i = 5; i <= 7; i++) {
			for (int j = 6; j <= 11; j++) {
				registerActor(new Grass(this, Orientation.UP, new DiscreteCoordinates(i, j)));
			}
		}
		
		registerActor(waterfall);
		
		HiddenBridge bridge = new HiddenBridge(this, Orientation.UP, new DiscreteCoordinates(15, 9));
		registerActor(bridge);
		registerActor(new Lever(this, Orientation.UP, new DiscreteCoordinates(19, 8), bridge));
		
		registerActor(new SignEntity("Si seulement quelqu'un avait un projectile pour atteindre ce levier...", true, this, Orientation.UP, new DiscreteCoordinates(12, 9)));
		
		registerActor(new LogMonster(this, Orientation.DOWN, new DiscreteCoordinates(11, 11)));
		registerActor(new LogMonster(this, Orientation.DOWN, new DiscreteCoordinates(4, 3)));
		
		//Lilypads
		registerActor(new Lilypads(new Vector(5, 18), 0));
		registerActor(new Lilypads(new Vector(16, 15), 2));
		registerActor(new Lilypads(new Vector(16, 9), 1));
		registerActor(new Lilypads(new Vector(15, 3), 3));
		registerActor(new Lilypads(new Vector(16, 2), 1));
		
		//Rocks
		registerActor(new Rock(1, this, new DiscreteCoordinates(5, 15)));
		registerActor(new Rock(3, this, new DiscreteCoordinates(9, 11)));
		registerActor(new Rock(2, this, new DiscreteCoordinates(3, 2)));
	}

	@Override
	public void reactivateSounds() {
		waterfall.reactivateSounds();
	}
}
