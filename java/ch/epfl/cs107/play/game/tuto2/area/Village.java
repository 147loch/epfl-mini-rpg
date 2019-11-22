package ch.epfl.cs107.play.game.tuto2.area;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Foreground;
import ch.epfl.cs107.play.game.tuto2.Tuto2Area;
import ch.epfl.cs107.play.game.tutos.actor.SimpleGhost;
import ch.epfl.cs107.play.math.Vector;

public class Village extends Tuto2Area {
	
	@Override
	public String getTitle() {
		return "zelda/Village";
	}

	@Override
	protected void createArea() {
		this.registerActor(new Background(this));
		registerActor(new Foreground(this)) ;
        registerActor(new SimpleGhost(new Vector(20, 10), "ghost.2"));
	}
}
