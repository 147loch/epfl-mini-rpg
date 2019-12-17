package ch.epfl.cs107.play.game.arpg.area;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Foreground;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.arpg.actor.areaentity.CaveDoor;
import ch.epfl.cs107.play.game.arpg.actor.areaentity.WaterFountain;
import ch.epfl.cs107.play.game.arpg.actor.collectable.Sword;
import ch.epfl.cs107.play.game.arpg.actor.entity.WaterEffect;
import ch.epfl.cs107.play.game.arpg.actor.npc.Emotion;
import ch.epfl.cs107.play.game.arpg.actor.npc.NPC;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;

public class Village extends ARPGArea {

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

		registerActor(new CaveDoor("GrotteMew", new DiscreteCoordinates(8, 3),
				this, Orientation.UP,
				new DiscreteCoordinates(25, 18)));

		registerActor(new WaterFountain(this, new DiscreteCoordinates(23, 8)));
		registerActor(new WaterEffect(new Vector(4, 1)));

		registerActor(new Sword(this, Orientation.UP, new DiscreteCoordinates(13, 18)));
		
		registerActor(new Npc("Les auteurs de ce jeu ne sont clairement pas scénaristes.", Emotion.EMPTY, this, Orientation.UP, new DiscreteCoordinates(25, 9)));
		registerActor(new Npc("Le village me donne une satisfaction primordiale.", Emotion.EMPTY, this, Orientation.UP, new DiscreteCoordinates(31, 16)));
		registerActor(new Npc("Il fait beau aujourd'hui. Mes genoux sont tout secs.", Emotion.EMPTY, this, Orientation.UP, new DiscreteCoordinates(10, 5)));
	}
}
