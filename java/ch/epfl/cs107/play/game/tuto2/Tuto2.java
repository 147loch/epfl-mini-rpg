package ch.epfl.cs107.play.game.tuto2;

import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.tuto1.area.Ferme;
import ch.epfl.cs107.play.game.tuto1.area.Village;
import ch.epfl.cs107.play.game.tutos.actor.GhostPlayer;
import ch.epfl.cs107.play.game.tutos.actor.SimpleGhost;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

public class Tuto2 extends AreaGame {

	private GhostPlayer player;
	
	public Tuto2() {
		player = new GhostPlayer();
	}
	
	private void createAreas() {
		addArea(new Ferme());
		addArea(new Village());
	}

	@Override
	public String getTitle() {
		return "Tuto2";
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
			this.setCurrentArea("zelda/Village", true);
			this.getCurrentArea().registerActor(player);
			this.getCurrentArea().setViewCandidate(player);
			
			return true;
		}
		else return false;
	}
}
