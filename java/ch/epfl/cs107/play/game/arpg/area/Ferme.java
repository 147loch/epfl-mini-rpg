package ch.epfl.cs107.play.game.arpg.area;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Foreground;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.arpg.actor.battle.monster.DarkLord;
import ch.epfl.cs107.play.game.arpg.actor.battle.monster.FlameSkull;
import ch.epfl.cs107.play.game.arpg.actor.battle.monster.LogMonster;
import ch.epfl.cs107.play.game.arpg.actor.collectable.ArrowItem;
import ch.epfl.cs107.play.game.arpg.actor.collectable.Bow;
import ch.epfl.cs107.play.game.arpg.actor.collectable.CastleKey;
import ch.epfl.cs107.play.game.arpg.actor.collectable.Heart;
import ch.epfl.cs107.play.game.arpg.actor.collectable.Staff;
import ch.epfl.cs107.play.game.arpg.actor.collectable.Sword;
import ch.epfl.cs107.play.game.arpg.actor.entity.FireSpell;
import ch.epfl.cs107.play.game.arpg.actor.entity.Grass;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;

public class Ferme extends ARPGArea {
	
	@Override
	public String getTitle() {
		return "zelda/Ferme";
	}

	@Override
	protected void createArea() {
		registerActor(new Background(this));
		registerActor(new Foreground(this));
		
		registerActor(new Door("zelda/Route", new DiscreteCoordinates(1, 15),
				Logic.TRUE, this, Orientation.RIGHT,
				new DiscreteCoordinates(19, 15), new DiscreteCoordinates(19, 16)));
		
		registerActor(new Door("zelda/Village", new DiscreteCoordinates(4, 18),
				Logic.TRUE, this, Orientation.DOWN,
				new DiscreteCoordinates(4, 0), new DiscreteCoordinates(5, 0)));
		
		registerActor(new Door("zelda/Village", new DiscreteCoordinates(14, 18),
				Logic.TRUE, this, Orientation.DOWN,
				new DiscreteCoordinates(13, 0), new DiscreteCoordinates(14, 0)));

		registerActor(new Door("PetalburgTimmy", new DiscreteCoordinates(3, 1),
				Logic.TRUE, this, Orientation.DOWN,
				new DiscreteCoordinates(6, 11)));

		registerActor(new FlameSkull(this, Orientation.UP, new DiscreteCoordinates(10, 10)));
		registerActor(new DarkLord(this, Orientation.DOWN, new DiscreteCoordinates(13, 10)));
		registerActor(new Heart(this, Orientation.DOWN, new DiscreteCoordinates(14, 10)));
		registerActor(new Grass(this, Orientation.UP, new DiscreteCoordinates(10, 9 )));
		registerActor(new Grass(this, Orientation.UP, new DiscreteCoordinates(9 , 10)));
		registerActor(new Grass(this, Orientation.UP, new DiscreteCoordinates(10 , 13)));
		registerActor(new Grass(this, Orientation.UP, new DiscreteCoordinates(11, 10)));

		registerActor(new FireSpell(this, Orientation.RIGHT, new DiscreteCoordinates(11, 6)));
		registerActor(new Grass(this, Orientation.UP, new DiscreteCoordinates(13, 6)));
		
		registerActor(new CastleKey(this, Orientation.UP, new DiscreteCoordinates(6, 6)));
		registerActor(new Bow(this, Orientation.UP, new DiscreteCoordinates(5, 6)));
		registerActor(new Staff(this, Orientation.UP, new DiscreteCoordinates(4, 6)));
		registerActor(new Sword(this, Orientation.UP, new DiscreteCoordinates(3, 6)));
		registerActor(new LogMonster(this, Orientation.UP, new DiscreteCoordinates(3, 7)));
		registerActor(new ArrowItem(this, Orientation.UP, new DiscreteCoordinates(4, 7)));
		registerActor(new FireSpell(this, Orientation.DOWN, new DiscreteCoordinates(7, 6), 3));
	}
}
