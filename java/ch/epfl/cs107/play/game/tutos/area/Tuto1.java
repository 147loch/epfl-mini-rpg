package ch.epfl.cs107.play.game.tutos.area;

import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.tutos.actor.SimpleGhost;
import ch.epfl.cs107.play.game.tutos.area.tuto1.Ferme;
import ch.epfl.cs107.play.game.tutos.area.tuto1.Village;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

public class Tuto1 extends AreaGame {

	private SimpleGhost player;
	
	public Tuto1() {
		player = new SimpleGhost(new Vector(10, 7), "ghost.1");
	}
	
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
		keyboardInput();
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
	
	private void keyboardInput() {
		Keyboard keyboard = getWindow().getKeyboard();
		if (keyboard.get(Keyboard.UP).isDown()) {
			player.moveUp();
		}
		if (keyboard.get(Keyboard.DOWN).isDown()) {
			player.moveDown();
		}
		if (keyboard.get(Keyboard.LEFT).isDown()) {
			player.moveLeft();
		}
		if (keyboard.get(Keyboard.RIGHT).isDown()) {
			player.moveRight();
		}
	}
	
}
