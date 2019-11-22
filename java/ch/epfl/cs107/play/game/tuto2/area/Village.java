package ch.epfl.cs107.play.game.tuto2.area;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.tuto2.Tuto2Area;
import ch.epfl.cs107.play.game.tutos.actor.SimpleGhost;
import ch.epfl.cs107.play.math.Vector;

public class Village extends Tuto2Area {
	
	private SimpleGhost ghost;
	
	public Village() {
		ghost = new SimpleGhost(new Vector(18, 7), "ghost.2");
	}
	
	@Override
	public String getTitle() {
		return "zelda/Village";
	}

	@Override
	protected void createArea() {
		this.registerActor(ghost);
		this.registerActor(new Background(this));
	}
}
