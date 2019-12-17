package ch.epfl.cs107.play.game.arpg.area;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Foreground;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.arpg.actor.areaentity.CaveDoor;
import ch.epfl.cs107.play.game.arpg.actor.areaentity.Rock;
import ch.epfl.cs107.play.game.arpg.actor.areaentity.SignEntity;
import ch.epfl.cs107.play.game.arpg.actor.areaentity.WaterFountain;
import ch.epfl.cs107.play.game.arpg.actor.collectable.Sword;
import ch.epfl.cs107.play.game.arpg.actor.entity.WaterEffect;
import ch.epfl.cs107.play.game.arpg.actor.npc.Emotion;
import ch.epfl.cs107.play.game.arpg.actor.npc.NPC;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.signal.logic.Logic;

public class Village extends ARPGArea {

	// We are doing this since it is a simple way of reactivating the sound
	private WaterFountain fountain = new WaterFountain(this, new DiscreteCoordinates(23, 8));

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

		registerActor(fountain);

		for (int i = 3; i <= 22; i++) {
			if (i % 3 != 0) {
				if (i % 5 == 0) {
					registerActor(new WaterEffect(new Vector(i, 2)));
				} else {
					registerActor(new WaterEffect(new Vector(i, 1)));
				}
			}
		}
		registerActor(new WaterEffect(new Vector(24, 3)));
		registerActor(new WaterEffect(new Vector(28, 3)));
		registerActor(new WaterEffect(new Vector(30, 5)));
		registerActor(new WaterEffect(new Vector(32, 2)));
		registerActor(new WaterEffect(new Vector(35, 7)));

		registerActor(new Sword(this, Orientation.UP, new DiscreteCoordinates(13, 18)));
		registerActor(new SignEntity("NE PAS DÉTRUIRE, GROTTE CONDAMNÉE !", true, this, Orientation.DOWN, new DiscreteCoordinates(27, 17)));
		
		//NPCs
		registerActor(new NPC("Les auteurs de ce jeu ne sont clairement pas scénaristes.", Emotion.EMPTY, this, Orientation.UP, new DiscreteCoordinates(25, 9)));
		registerActor(new NPC("Le village me donne une satisfaction primordiale.", Emotion.EMPTY, this, Orientation.UP, new DiscreteCoordinates(31, 16)));
		registerActor(new NPC("Il fait beau aujourd'hui. Mes genoux sont tout secs.", Emotion.EMPTY, this, Orientation.UP, new DiscreteCoordinates(10, 5)));
		
		//Rocks
		registerActor(new Rock(0, this, new DiscreteCoordinates(29, 12)));
		registerActor(new Rock(1, this, new DiscreteCoordinates(20, 15)));
		registerActor(new Rock(2, this, new DiscreteCoordinates(15, 5)));
		registerActor(new Rock(3, this, new DiscreteCoordinates(8, 4)));
		registerActor(new Rock(4, this, new DiscreteCoordinates(5, 14)));
		registerActor(new Rock(2, this, new DiscreteCoordinates(9, 13)));
	}

	@Override
	public void reactivateSounds() {
		fountain.reactivateSounds();
	}
}
