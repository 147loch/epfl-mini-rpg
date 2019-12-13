package ch.epfl.cs107.play.game.arpg.actor.puzzle;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.arpg.actor.monster.FlyableEntity;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.window.Canvas;

public class Activator extends AreaEntity implements FlyableEntity {

	private static final int ANIMATION_FRAME_LENGTH = 6;
	
	private Animation animation;
	private Activatable entityActivatable;
	private boolean isAlreadyActive;
	
	public Activator(Area area, Orientation orientation, DiscreteCoordinates position, Activatable entityActivatable) {
		super(area, orientation, position);
		
		this.entityActivatable = entityActivatable;
		isAlreadyActive = false;
		
		Sprite[] sprites = new Sprite[ANIMATION_FRAME_LENGTH];
		
		for (int i = 0; i < ANIMATION_FRAME_LENGTH; i++) {
			sprites[i] = new RPGSprite("zelda/orb", 1.f, 1.f, this, new RegionOfInterest((i*32), 64, 32, 32));
		}
		animation = new Animation(sprites.length, sprites, true);
	}
	
	public void active() {
		if (!isAlreadyActive)
		{
			entityActivatable.activeEntity();
			isAlreadyActive = true;
		}
	}
	
	@Override
	public void update(float deltaTime) {
		animation.update(deltaTime);
		super.update(deltaTime);
	}

	@Override
	public List<DiscreteCoordinates> getCurrentCells() {
		return Collections.singletonList(getCurrentMainCellCoordinates());
	}

	@Override
	public boolean takeCellSpace() {
		return false;
	}

	@Override
	public boolean isCellInteractable() {
		return true;
	}

	@Override
	public boolean isViewInteractable() {
		return true;
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		((ARPGInteractionVisitor)v).interactWith(this);
	}

	@Override
	public void draw(Canvas canvas) {
		if (!isAlreadyActive)
			animation.draw(canvas);
	}
}
