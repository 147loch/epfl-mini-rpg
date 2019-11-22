package ch.epfl.cs107.play.game.tuto2;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;

abstract public class Tuto2Area extends Area {

	private Tuto2Behavior behavior;

	protected abstract void createArea();

	@Override
	public final float getCameraScaleFactor() { return Tuto2.CAMERA_SCALE_FACTOR; }

	@Override
    public boolean begin(Window window, FileSystem fileSystem) {
        if (super.begin(window, fileSystem)) {
        	behavior = new Tuto2Behavior(window, getTitle());
           	setBehavior(behavior);
            createArea();
            return true;
        }
        return false;
	 }

	 public boolean isDoor(DiscreteCoordinates coord) {
		return (behavior.isDoor(coord));
	 }
}
