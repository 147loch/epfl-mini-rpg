package ch.epfl.cs107.play.game.arpg.actor.battle.monster;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.arpg.actor.ARPGPlayer;
import ch.epfl.cs107.play.game.arpg.actor.FlyableEntity;
import ch.epfl.cs107.play.game.arpg.actor.entity.Bomb;
import ch.epfl.cs107.play.game.arpg.actor.entity.Grass;
import ch.epfl.cs107.play.game.arpg.actor.battle.DamageType;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RandomGenerator;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class FlameSkull extends MonsterEntity implements FlyableEntity {

	private static final int MIN_LIFE_TIME = 2;
	private static final int MAX_LIFE_TIME = 8;

	private static final int ANIMATION_DURATION = 4;
	private static final int MOVEMENT_FRAMES = 8;

	private static final float PLAYER_ATTACK_DAMAGE = 1.f;

	private static final DamageType[] VULNERABILITIES = { DamageType.PHYSICAL, DamageType.MAGICAL };

	private Animation[] animations;
	private Animation currentAnimation;
	
	private ARPGFlameSkullHandler handler;

	private float remainingTime;

	public FlameSkull(Area area, Orientation orientation, DiscreteCoordinates position) {
		super(area, orientation, position, 2.f, VULNERABILITIES);
		
		Sprite[][] sprites = RPGSprite.extractSprites(
				"zelda/flameSkull", 3, 2, 2, this ,
				32, 32, new Vector(-0.5f, 0),
				new Orientation[] {Orientation.UP , Orientation.LEFT , Orientation.DOWN, Orientation.RIGHT}
			);
		animations = RPGSprite.createAnimations(ANIMATION_DURATION, sprites);

		currentAnimation = animations[orientation.ordinal()];

		remainingTime = RandomGenerator.getInstance().nextInt(MAX_LIFE_TIME - MIN_LIFE_TIME + 1) + MIN_LIFE_TIME;

		handler = new ARPGFlameSkullHandler();
	}

	// MonsterEntity events
	@Override protected void handleDamageEvent(float damageTook) {}
	@Override protected void handleDeathDropEvent() {}

	// Normal definitions
	@Override
	public void update(float deltaTime) {
		if (remainingTime <= 0) {
			setCurrentState(State.DEAD);
		} else {
			remainingTime -= deltaTime;
		}

		currentAnimation = animations[getOrientation().ordinal()];

		if (getCurrentState() == State.IDLE) {
			if (this.isDisplacementOccurs())
				currentAnimation.update(deltaTime);
			else if (RandomGenerator.getInstance().nextDouble() > 0.6) {
				resetMotion();
				orientate(Orientation.fromInt(RandomGenerator.getInstance().nextInt(4)));
			} else
				move(MOVEMENT_FRAMES);
		}

		super.update(deltaTime);
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
		if (!State.DEAD.equals(getCurrentState()) && !currentAnimation.isCompleted()) {
			currentAnimation.draw(canvas);
		}
		super.draw(canvas);
	}

	@Override
	public List<DiscreteCoordinates> getFieldOfViewCells() {
		return Collections.singletonList(getCurrentMainCellCoordinates().jump(getOrientation().toVector()));
	}

	@Override public boolean wantsCellInteraction() { return false; }
	@Override public boolean wantsViewInteraction() { return true; }

	@Override public boolean takeCellSpace() { return false; }

	private class ARPGFlameSkullHandler implements ARPGInteractionVisitor {
		@Override
		public void interactWith(Grass grass) {
			grass.cut(false);
		}

		@Override
		public void interactWith(Bomb bomb) {
			bomb.explode();
		}

		@Override
		public void interactWith(ARPGPlayer player) {
			if (!isDisplacementOccurs())
				player.takeDamage(PLAYER_ATTACK_DAMAGE);
		}
	}
}
