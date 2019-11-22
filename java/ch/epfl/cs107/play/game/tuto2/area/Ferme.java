package ch.epfl.cs107.play.game.tuto2.area;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Foreground;
import ch.epfl.cs107.play.game.tuto2.Tuto2Area;

public class Ferme extends Tuto2Area {
	
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
