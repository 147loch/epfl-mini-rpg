package ch.epfl.cs107.play.game.arpg.area;

import ch.epfl.cs107.play.Play;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.arpg.ARPGBehavior;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.window.Window;

public abstract class ARPGArea extends Area {

	private final static float CAMERA_SCALE_FACTOR = 15.f;

	private Window window;

	/**
	 * This method is used to create the different Areas
	 */
	protected abstract void createArea();

	/**
	 * This method is used to reactivate sounds for Areas with Sound Loops Items
	 */
	public void reactivateSounds() {}
	
	/**
	 * This method is used to restart the game
	 */
	public void restart() {
		window.dispose();
		Play.main(null);
	}
	
	@Override
	public String getTitle() {
		return "ARPGArea";
	}

	@Override
	public float getCameraScaleFactor() {
		return CAMERA_SCALE_FACTOR;
	}
	
	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		if (super.begin(window, fileSystem)) {
			ARPGBehavior behavior = new ARPGBehavior(window, this.getTitle());
			setBehavior(behavior);
			createArea();
			this.window = window;
			return true;
		}
		return false;
	}
}
