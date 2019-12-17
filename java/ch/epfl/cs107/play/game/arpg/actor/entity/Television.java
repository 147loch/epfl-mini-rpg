package ch.epfl.cs107.play.game.arpg.actor.entity;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.Sign;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Television extends Sign {

	public Television(Area area, Orientation orientation, DiscreteCoordinates position) {
		super("Tiens c'est marrant, il y a une gameboy à côté.", area, orientation, position);
	}

	
	@Override
    public void acceptInteraction(AreaInteractionVisitor v) {
        ((ARPGInteractionVisitor)v).interactWith(this);
    }
}
