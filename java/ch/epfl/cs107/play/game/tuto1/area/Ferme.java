package ch.epfl.cs107.play.game.tuto1.area;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.tuto1.SimpleArea;

public class Ferme extends SimpleArea {
	
	@Override
	public String getTitle() {
		return "zelda/Ferme";
	}

	@Override
	protected void createArea() {
		this.registerActor(new Background(this));
	}
	
}
