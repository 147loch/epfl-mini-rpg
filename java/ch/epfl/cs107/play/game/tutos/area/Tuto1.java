package ch.epfl.cs107.play.game.tutos.area;

import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.tutos.actor.SimpleGhost;
import ch.epfl.cs107.play.game.tutos.area.tuto1.Ferme;
import ch.epfl.cs107.play.game.tutos.area.tuto1.Village;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Window;

public class Tuto1 extends AreaGame {

	private SimpleGhost player;
	
	private void createAreas() {
		addArea(new Ferme());
		addArea(new Village());
	}

	@Override
	public String getTitle() {
		return "Tuto1";
	}
	
	@Override
	public void end() {
		
	}
	
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
	}
	
	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		if (super.begin(window , fileSystem)) {
			createAreas();
			this.setCurrentArea("zelda/Ferme", true);
			
			player = new SimpleGhost(new Vector(18, 7), "ghost.1");
			this.getCurrentArea().registerActor(player);
			this.getCurrentArea().setViewCandidate(player);
			
			return true;
		}
		else return false;
	}
	
}
