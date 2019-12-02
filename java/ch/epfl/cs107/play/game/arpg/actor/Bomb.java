package ch.epfl.cs107.play.game.arpg.actor;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.window.Canvas;

public class Bomb extends AreaEntity implements Interactor {

	private final int TIME_BEFORE_EXPLOSION = 4;
	
	private Animation bombAnimation;
	private Animation explosionAnimation;
	private float remainingTime;
	private boolean isExploding;
	
	private int bombAnimationSpeedIndex;
	
	private ARPGBombHandler handler;
	
	public Bomb(Area area, Orientation orientation, DiscreteCoordinates position) {
		super(area, orientation, position);
		isExploding = false;
		
		Sprite[] sprites = new Sprite[2];
		for (int i = 0; i < 2; i++) {
			sprites[i] = new RPGSprite("zelda/bomb", 1.f, 1.f, this, new RegionOfInterest(16*i, 0, 16, 16));
		}
		bombAnimation = new Animation(sprites.length, sprites, true);
		bombAnimationSpeedIndex = 0;
		
		handler = new ARPGBombHandler();
		
		Sprite[] spritesExplosion = new Sprite[7];
		for (int i = 0; i < 7; i++) {
			spritesExplosion[i] = new RPGSprite("zelda/explosion", 1.f, 1.f, this, new RegionOfInterest(32*i, 0, 32, 32));
		}
		explosionAnimation = new Animation(spritesExplosion.length, spritesExplosion, false);
		explosionAnimation.setSpeedFactor(2);
		
		remainingTime = TIME_BEFORE_EXPLOSION;
	}

	@Override
	public void update(float deltaTime) {
		if (remainingTime <= 0) {
			if (!isExploding)
				isExploding = true;
			explosionAnimation.update(deltaTime);
		} else {
			remainingTime -= deltaTime;
		}
		
		if (bombAnimationSpeedIndex >= Math.floor(remainingTime)) {
			bombAnimation.update(deltaTime);
			bombAnimationSpeedIndex = 0;
		}
		else
			bombAnimationSpeedIndex++;
		
		if (explosionAnimation.isCompleted()) {
			this.getOwnerArea().unregisterActor(this);
		}
		
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
		return true;
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		((ARPGInteractionVisitor)v).interactWith(this);
	}
	
	@Override
	public void interactWith(Interactable other) {
		other.acceptInteraction(handler);
	}

	@Override
	public void draw(Canvas canvas) {
		if (isExploding)
			explosionAnimation.draw(canvas);
		else
			bombAnimation.draw(canvas);
	}
	
	@Override
	public List<DiscreteCoordinates> getFieldOfViewCells() {
		return Collections.list(this.getCurrentMainCellCoordinates());
	}

	@Override
	public boolean wantsCellInteraction() {
		return true;
	}

	@Override
	public boolean wantsViewInteraction() {
		return true;
	}
	
	private class ARPGBombHandler implements ARPGInteractionVisitor {
		@Override
		public void interactWith(Grass grass) {
			grass.setInactive();
		}
	}
}
