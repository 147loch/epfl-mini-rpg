package ch.epfl.cs107.play.game.arpg.area;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.arpg.ARPGBehavior;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.window.Window;

public abstract class ARPGArea extends Area {

	private final static float CAMERA_SCALE_FACTOR = 15.f;

	protected abstract void createArea();
	
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
			return true;
		}
		return false;
	}

}
