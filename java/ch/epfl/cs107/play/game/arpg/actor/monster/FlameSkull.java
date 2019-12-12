package ch.epfl.cs107.play.game.arpg.actor.monster;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.arpg.actor.ARPGPlayer;
import ch.epfl.cs107.play.game.arpg.actor.Bomb;
import ch.epfl.cs107.play.game.arpg.actor.Grass;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RandomGenerator;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class FlameSkull extends MonsterEntity {

	private static final int MIN_LIFE_TIME = 0;
	private static final int MAX_LIFE_TIME = 8;
	private static final int ANIMATION_DURATION = 4;
	private static final float PLAYER_ATTACK_DAMAGE = 1.f;
	private static final DamageType[] VULNERABILITIES = {DamageType.PHYSICAL, DamageType.MAGICAL};

	private Animation[] animations;
	
	private ARPGFlameSkullHandler handler;

	private float remainingTime;

	public FlameSkull(Area area, Orientation orientation, DiscreteCoordinates position) {
		super(area, orientation, position, 2.f, VULNERABILITIES);
		
		Sprite[][] sprites = RPGSprite.extractSprites("zelda/flameSkull", 3, 2, 2, this , 32, 32, new Vector(-0.5f, 0), new Orientation[]
				{Orientation.UP , Orientation.LEFT , Orientation.DOWN, Orientation.RIGHT});
		animations = RPGSprite.createAnimations(ANIMATION_DURATION, sprites);
		setAnimation(animations[0]);

		remainingTime = RandomGenerator.getInstance().nextInt(MAX_LIFE_TIME + 1) + MIN_LIFE_TIME;

		handler = new ARPGFlameSkullHandler();
	}
	
	@Override
	public void update(float deltaTime) {
		if (remainingTime <= 0) {
			setState(Behavior.DEAD);
		} else {
			remainingTime -= deltaTime;
		}

		setAnimation(animations[getOrientation().ordinal()]);
		
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
		super.draw(canvas);
	}


	@Override
	public List<DiscreteCoordinates> getFieldOfViewCells() {
		return Collections.singletonList(getCurrentMainCellCoordinates().jump(getOrientation().toVector()));
	}

	@Override
	public boolean wantsCellInteraction() {
		return false;
	}

	@Override
	public boolean wantsViewInteraction() {
		return true;
	}
	
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
