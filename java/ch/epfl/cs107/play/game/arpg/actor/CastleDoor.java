package ch.epfl.cs107.play.game.arpg.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Canvas;

public class CastleDoor extends Door {

	private RPGSprite doorOpened;
	private RPGSprite doorClosed;
	
	private boolean isOpened;
	
	public CastleDoor(String destination, DiscreteCoordinates otherSideCoordinates, Area area,
			Orientation orientation, DiscreteCoordinates position, DiscreteCoordinates secondPosition) {
		super(destination, otherSideCoordinates, Logic.TRUE, area, orientation, position, secondPosition);
		
		doorOpened = new RPGSprite("zelda/castleDoor.open", 2.f, 2.f, this);
		doorClosed = new RPGSprite("zelda/castleDoor.close", 2.f, 2.f, this);
		
		isOpened = false;
	}
	
	public boolean changeSignal() {
		isOpened = !isOpened;
		return isOpened;
	}
	
	@Override
	public boolean takeCellSpace() {
		return !isOpened;
	}
	
	@Override
	public boolean isViewInteractable() {
		return true;
	}
	
	@Override
	public void draw(Canvas canvas) {
		if (isOpened)
			doorOpened.draw(canvas);
		else
			doorClosed.draw(canvas);
	}
	
	
}
