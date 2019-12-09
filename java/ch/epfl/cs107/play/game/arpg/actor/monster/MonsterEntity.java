package ch.epfl.cs107.play.game.arpg.actor.monster;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public abstract class MonsterEntity extends AreaEntity implements FlyableEntity {

	private enum Behavior {
		IDLE,
		ATTACK;
	}
	
	private static final int ANIMATION_VANISH_FRAME_LENGTH = 7;
	
	private Animation animationVanish;
	private Animation currentAnimation;
	private int indexAnimationVanish;
	
	private float maxHp;
	private float currentHp;
	
	private boolean isDead;
	
	public MonsterEntity(Area area, Orientation orientation, DiscreteCoordinates position, float Hp) {
		super(area, orientation, position);
		
		maxHp = Hp;
		currentHp = maxHp;
		isDead = false;
		
		RPGSprite[] sprites = new RPGSprite[ANIMATION_VANISH_FRAME_LENGTH];
		
		for (int i = 0; i < ANIMATION_VANISH_FRAME_LENGTH; i++) {
			sprites[i] = new RPGSprite("zelda/vanish", 2.f, 2.f, this, new RegionOfInterest((i*32), 0, 32, 32));
		}
		animationVanish = new Animation(1, sprites, false);
		animationVanish.setAnchor(new Vector(this.getTransform().getX().getY() - 0.5f, this.getTransform().getX().getY()));
		indexAnimationVanish = 0;
	}
	
	protected void setAnimation(Animation animation) {
		currentAnimation = animation;
	}
	
	protected float getCurrentHp() {
		return currentHp;
	}
	
	protected boolean isDead() {
		return isDead;
	}
	
	public void takeDamage() {
		currentHp -= 0.5f;
	}
	
	@Override
	public void update(float deltaTime) {
		
		if (currentHp <= 0) {
			isDead = true;
			currentHp = 0;
		}
		
		if (isDead) {
			animationVanish.update(deltaTime);
			indexAnimationVanish++;
		} else
			currentAnimation.update(deltaTime);
		
		if (indexAnimationVanish == ANIMATION_VANISH_FRAME_LENGTH)
			getOwnerArea().unregisterActor(this);
		
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
		return false;
	}

	@Override
	public boolean isViewInteractable() {
		return true;
	}

	@Override
	public void draw(Canvas canvas) {
		if (isDead) {
			animationVanish.draw(canvas);
		} else {
			currentAnimation.draw(canvas);
		}
	}

}
