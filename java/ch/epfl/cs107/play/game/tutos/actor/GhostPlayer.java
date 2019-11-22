package ch.epfl.cs107.play.game.tutos.actor;

import java.awt.*;
import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.*;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.tuto2.Tuto2Behavior.Tuto2Cell;
import ch.epfl.cs107.play.game.tuto2.Tuto2Behavior.Tuto2CellType;
import ch.epfl.cs107.play.game.tutosSolution.area.Tuto2Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class GhostPlayer extends MovableAreaEntity {

	private float hp;
	private TextGraphics message;
	private Sprite sprite;
	private boolean isPassingDoor;
	
	private final static int ANIMATION_DURATION = 8;
	
	public GhostPlayer(Area area, Orientation orientation, DiscreteCoordinates coordinates, String spriteString) {
		super(area, orientation, coordinates);

		this.hp = 10;
		message = new TextGraphics(Integer.toString((int)hp), 0.4f, Color.BLUE);
		message.setParent(this);
		message.setAnchor(new Vector(-0.3f, 0.1f));
		sprite = new Sprite(spriteString, 1.f, 1.f, this);

		resetMotion();
	}
	
	@Override
	public void update(float deltaTime) {
		if (hp > 0) {
			hp -=deltaTime;
			message.setText(Integer.toString((int)hp));
		}
		if (hp < 0) hp = 0.f;
		Keyboard keyboard= getOwnerArea().getKeyboard();
		moveOrientate(Orientation.LEFT, keyboard.get(Keyboard.LEFT));
		moveOrientate(Orientation.UP, keyboard.get(Keyboard.UP));
		moveOrientate(Orientation.RIGHT, keyboard.get(Keyboard.RIGHT));
		moveOrientate(Orientation.DOWN, keyboard.get(Keyboard.DOWN));

		super.update(deltaTime);

		//List<DiscreteCoordinates> coords = getEnteredCells();
		List<DiscreteCoordinates> coords = getCurrentCells();
		if (coords != null) {
			for (DiscreteCoordinates c : coords) {
				if (((Tuto2Area)getOwnerArea()).isDoor(c)) setIsPassingDoor();
			}
		}
	}

	private void moveOrientate(Orientation orientation, Button b){
		if(b.isDown()) {
			if(getOrientation() == orientation) move(ANIMATION_DURATION);
			else orientate(orientation);
		}
	}

	protected void setIsPassingDoor() { isPassingDoor = true; }
	public boolean isPassingDoor() { return isPassingDoor; }

	public void resetDoorState() { isPassingDoor = false; }
	public void leaveArea() { getOwnerArea().unregisterActor(this); }

	public void enterArea(Area area, DiscreteCoordinates position) {
		area.registerActor(this);
		area.setViewCandidate(this);
		setOwnerArea(area);
		setCurrentPosition(position.toVector());
		resetMotion();
	}

	@Override
	public void draw(Canvas canvas) {
		sprite.draw(canvas);
		message.draw(canvas);
	}

	public void strengthen() { hp = 10; }

	@Override
	public List<DiscreteCoordinates> getCurrentCells() {
		return Collections.singletonList(getCurrentMainCellCoordinates());
	}

	@Override
	public boolean takeCellSpace() { return true; }

	@Override
	public boolean isCellInteractable() { return true;	}

	@Override
	public boolean isViewInteractable() { return true; }

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {}
}
