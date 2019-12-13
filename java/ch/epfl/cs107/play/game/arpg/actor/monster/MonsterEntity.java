package ch.epfl.cs107.play.game.arpg.actor.monster;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RandomGenerator;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public abstract class MonsterEntity extends MovableAreaEntity implements FlyableEntity, Interactor {

	protected enum Behavior {
		IDLE,
		ATTACK,
		DEAD,
		FALLING_ASLEEP,
		WAKING_UP,
		SLEEP
	}

	private static final int ANIMATION_VANISH_FRAME_LENGTH = 7;
	protected static final int MOVEMENT_FRAMES = 8;
	
	private Animation animationVanish;
	private Animation currentAnimation;
	private int indexAnimationVanish;
	
	private float maxHp;
	private float currentHp;
	private Behavior state;
	private DamageType vulnerabilities[];
	
	public MonsterEntity(Area area, Orientation orientation, DiscreteCoordinates position, float Hp, DamageType[] vulnerabilities) {
		super(area, orientation, position);
		
		maxHp = Hp;
		currentHp = maxHp;
		this.vulnerabilities = vulnerabilities;
		
		state = Behavior.IDLE;
		
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
	protected Animation getAnimation() { return currentAnimation; }

	protected float getCurrentHp() {
		return currentHp;
	}
	
	public DamageType[] getVulnerabilities() {
		return vulnerabilities;
	}

	protected void setState(Behavior state) { this.state = state; }
	protected Behavior getState() { return this.state; }
	
	public void takeDamage() {
		currentHp -= 0.5f;
	}
	
	@Override
	public void update(float deltaTime) {
		
		if (currentHp <= 0) {
			state = Behavior.DEAD;
			currentHp = 0;
		}
		
		if (indexAnimationVanish == ANIMATION_VANISH_FRAME_LENGTH)
			getOwnerArea().unregisterActor(this);
		
		switch (state) {
			case IDLE:
				if (this.isDisplacementOccurs())
					currentAnimation.update(deltaTime);
				else
					handleMovement();
				break;
			case DEAD:
				animationVanish.update(deltaTime);
				indexAnimationVanish++;
				break;
			default:
				if (currentAnimation != null)
					currentAnimation.update(deltaTime);
				break;
		}
		
		super.update(deltaTime);
	}

	protected abstract void handleMovement();

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
	public void draw(Canvas canvas) {
		if (state.equals(Behavior.DEAD)) {
			animationVanish.draw(canvas);
		} else {
			currentAnimation.draw(canvas);
		}
	}
}
