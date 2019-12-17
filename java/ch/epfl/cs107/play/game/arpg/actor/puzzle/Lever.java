package ch.epfl.cs107.play.game.arpg.actor.puzzle;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.arpg.actor.FlyableEntity;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.window.Canvas;

public class Lever extends AreaEntity {

	private static final int ANIMATION_FRAME_LENGTH = 6;
	
	private Sprite onSprite;
	private Sprite offSprite;
	private Activatable entityActivatable;
	private boolean isAlreadyActive;
	
	public Lever(Area area, Orientation orientation, DiscreteCoordinates position, Activatable entityActivatable) {
		super(area, orientation, position);
		
		this.entityActivatable = entityActivatable;
		isAlreadyActive = false;

		offSprite = new RPGSprite("custom/lever", 1.f, 1.f, this, new RegionOfInterest(0, 0, 16, 16));
		onSprite = new RPGSprite("custom/lever", 1.f, 1.f, this, new RegionOfInterest(16, 0, 16, 16));
	}
	
	public void active() {
		if (!isAlreadyActive) {
			entityActivatable.activeEntity();
			isAlreadyActive = true;
		}
	}
	
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
	}

	@Override
	public List<DiscreteCoordinates> getCurrentCells() {
		return Collections.singletonList(getCurrentMainCellCoordinates());
	}

	@Override
	public boolean takeCellSpace() {
		return true;
	}

	@Override
	public boolean isCellInteractable() {
		return true;
	}

	@Override
	public boolean isViewInteractable() {
		return false;
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		((ARPGInteractionVisitor)v).interactWith(this);
	}

	@Override
	public void draw(Canvas canvas) {
		if (isAlreadyActive)
			onSprite.draw(canvas);
		else
			offSprite.draw(canvas);
	}
}
