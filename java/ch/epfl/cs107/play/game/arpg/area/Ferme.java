package ch.epfl.cs107.play.game.arpg.area;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Foreground;

public class Ferme extends ARPGArea {
	
	@Override
	public String getTitle() {
		return "zelda/Ferme";
	}

	@Override
	protected void createArea() {
		this.registerActor(new Background(this));
		this.registerActor(new Foreground(this));
	}
}
