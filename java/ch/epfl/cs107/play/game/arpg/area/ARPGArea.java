package ch.epfl.cs107.play.game.arpg.area;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.AreaBehavior;
import ch.epfl.cs107.play.game.arpg.ARPGBehavior;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.window.Window;

public abstract class ARPGArea extends Area {

	private AreaBehavior behavior;

	protected abstract void createArea();
	
	@Override
	public String getTitle() {
		return "ARPGArea";
	}

	@Override
	public float getCameraScaleFactor() {
		return 10.f;
	}

	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		if (super.begin(window, fileSystem)) {
			behavior = new ARPGBehavior(window, this.getTitle());
			setBehavior(behavior);
			createArea();
			return true;
		}
		return false;
	}

}
