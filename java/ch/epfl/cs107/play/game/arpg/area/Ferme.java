package ch.epfl.cs107.play.game.arpg.area;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Foreground;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.arpg.actor.areaentity.SignEntity;
import ch.epfl.cs107.play.game.arpg.actor.collectable.ArrowItem;
import ch.epfl.cs107.play.game.arpg.actor.collectable.Bow;
import ch.epfl.cs107.play.game.arpg.actor.collectable.Staff;
import ch.epfl.cs107.play.game.arpg.actor.npc.Emotion;
import ch.epfl.cs107.play.game.arpg.actor.npc.Npc;
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
		
		registerActor(new Bow(this, Orientation.UP, new DiscreteCoordinates(5, 6)));
		registerActor(new Staff(this, Orientation.UP, new DiscreteCoordinates(4, 6)));
		registerActor(new ArrowItem(this, Orientation.UP, new DiscreteCoordinates(4, 7)));
		
		registerActor(new SignEntity("Maison du futur héro", true, this, Orientation.UP, new DiscreteCoordinates(3, 11)));
		
		registerActor(new Npc("C'est bête, j'ai perdu mon épée. Garde la si tu la trouves.", Emotion.INTERROGATION, this, Orientation.UP, new DiscreteCoordinates(6, 7)));
	}
}
