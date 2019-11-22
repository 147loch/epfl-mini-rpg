package ch.epfl.cs107.play.game.tutos.actor;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.tuto2.Tuto2Behavior.Tuto2Cell;
import ch.epfl.cs107.play.game.tuto2.Tuto2Behavior.Tuto2CellType;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class GhostPlayer extends MovableAreaEntity {
	
	private Sprite sprite;
	private boolean onDoor;
	
	private final static int ANIMATION_DURATION = 8;
	
	public GhostPlayer(Area area, Orientation orientation, DiscreteCoordinates coordinates, String spriteString) {
		super(area, orientation, coordinates);
		sprite = new Sprite(spriteString, 1, 1.f, this);
		onDoor = false;
	}
	
	public void enterArea(Area area, DiscreteCoordinates position) {
		this.getOwnerArea().registerActor(this);
		this.setCurrentPosition(position.toVector());
		this.resetMotion();
	}
	
	public void quitArea() {
		this.getOwnerArea().unregisterActor(this);
	}
	
	public void setOnDoor(boolean d) {
		onDoor = d;
	}
	
	@Override
	public void update(float deltaTime) {
		if (keyboard.get(Keyboard.LEFT).isDown()) {
			if (getOrientation() == Orientation.LEFT) {
				this.move(ANIMATION_DURATION);
			} else {
				orientate(Orientation.LEFT);
			}
		}
		
		super.update(deltaTime);
		
		if (((Tuto2Cell)getCurrentCells()).getType() == Tuto2CellType.DOOR) {
			this.setOnDoor(true);
		} else {
			this.setOnDoor(false);
		}
	}
	
	@Override
	public List<DiscreteCoordinates> getCurrentCells() {
		return Collections.singletonList(getCurrentMainCellCoordinates());
	}

	@Override
	public boolean takeCellSpace() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCellInteractable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isViewInteractable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Canvas canvas) {
		sprite.draw(canvas);
	}
}
