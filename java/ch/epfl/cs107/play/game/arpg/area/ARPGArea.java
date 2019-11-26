package ch.epfl.cs107.play.game.arpg.area;

import ch.epfl.cs107.play.game.areagame.Area;

public abstract class ARPGArea extends Area {

	protected abstract void createArea();
	
	@Override
	public String getTitle() {
		return "ARPGArea";
	}

	@Override
	public float getCameraScaleFactor() {
		return 10.f;
	}

}
