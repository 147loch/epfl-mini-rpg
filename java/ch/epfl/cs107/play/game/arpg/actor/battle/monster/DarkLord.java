package ch.epfl.cs107.play.game.arpg.actor.battle.monster;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.arpg.actor.ARPGPlayer;
import ch.epfl.cs107.play.game.arpg.actor.battle.DamageType;
import ch.epfl.cs107.play.game.arpg.actor.collectable.CastleKey;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RandomGenerator;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class DarkLord extends MonsterEntity {
	
	private static final DamageType[] VULNERABILITIES = { DamageType.MAGICAL };
	private static final int ANIMATION_DURATION = 4;
	private static final int MOVEMENT_FRAMES = 16;
    private static final int ANIMATION_VANISH_FRAME_LENGTH = 7;
	
	private ARPGDarkLordHandler handler;
	
	private Animation[] animationsIdle;
	private Animation[] animationsAttack;
	private Animation animationIdle;
	private Animation animationAttack;
	private Animation animationVanishTeleportation;

	public DarkLord(Area area, Orientation orientation, DiscreteCoordinates coordinates) {
		super(area, orientation, coordinates, 2.f, VULNERABILITIES);
		
		handler = new ARPGDarkLordHandler();
		
		Sprite[][] spritesIdle = RPGSprite.extractSprites("zelda/darkLord", 3, 2, 2, this, 32, 32, new Vector(-0.5f, 0),
				new Orientation[] {Orientation.UP , Orientation.LEFT , Orientation.DOWN, Orientation.RIGHT});
		animationsIdle = RPGSprite.createAnimations(ANIMATION_DURATION, spritesIdle);
		
		Sprite[][] spritesAttack = RPGSprite.extractSprites("zelda/darkLord.spell", 3, 2, 2, this, 32, 32, new Vector(-0.5f, 0),
				new Orientation[] {Orientation.UP , Orientation.LEFT , Orientation.DOWN, Orientation.RIGHT});
		animationsAttack = RPGSprite.createAnimations(ANIMATION_DURATION, spritesAttack, false);
		
		RPGSprite[] vanishAnimationSprites = new RPGSprite[ANIMATION_VANISH_FRAME_LENGTH];
        for (int i = 0; i < ANIMATION_VANISH_FRAME_LENGTH; i++) {
        	vanishAnimationSprites[i] = new RPGSprite("zelda/vanishTeleportation", 2.f, 2.f, this, new RegionOfInterest((i*32), 0, 32, 32), new Vector(-0.5f, 0));
        }
        animationVanishTeleportation = new Animation(1, vanishAnimationSprites, false);

        animationIdle = animationsIdle[orientation.ordinal()];
        animationAttack = animationsAttack[orientation.ordinal()];
	}
	
	private void teleportation(int x, int y) {
		resetMotion();
		setCurrentState(State.IDLE);
		setCurrentPosition(new Vector(x, y));
	}

	@Override
	public void update(float deltaTime) {
		switch (getCurrentState()) {
			case IDLE:
				animationIdle = animationsIdle[getOrientation().ordinal()];
				if (this.isDisplacementOccurs())
					animationIdle.update(deltaTime);
				else if (RandomGenerator.getInstance().nextDouble() > 0.6) {
					resetMotion();
					orientate(Orientation.fromInt(RandomGenerator.getInstance().nextInt(4)));
				} else
					move(MOVEMENT_FRAMES);
				break;
			case IS_GOING_TO_TELEPORT:
				animationAttack = animationsAttack[getOrientation().ordinal()];
				animationAttack.update(deltaTime);
				break;
			case TELEPORTATION:
				animationVanishTeleportation.update(deltaTime);
				if (animationVanishTeleportation.isCompleted())
					teleportation(5, 5);
				break;
			default:
				break;
		}
		super.update(deltaTime);
	}
	
	@Override
	public List<DiscreteCoordinates> getFieldOfViewCells() {
		return Collections.singletonList(getCurrentMainCellCoordinates().jump(getOrientation().toVector()));
	}

	@Override
	public boolean wantsCellInteraction() {
		return true;
	}

	@Override
	public boolean wantsViewInteraction() {
		return true;
	}

	@Override
	public void interactWith(Interactable other) {
		other.acceptInteraction(handler);
	}
	
	@Override
	public void draw(Canvas canvas) {
		if (getCurrentState().equals(State.IDLE)) {
			animationIdle.draw(canvas);
			animationVanishTeleportation.reset();
			animationAttack.reset();
		}
		else if (getCurrentState().equals(State.IS_GOING_TO_TELEPORT)) {
				if (!animationAttack.isCompleted())
					animationAttack.draw(canvas);
				else
					setCurrentState(State.TELEPORTATION);
		} else if (getCurrentState().equals(State.TELEPORTATION)) {
			if (!animationVanishTeleportation.isCompleted())
				animationVanishTeleportation.draw(canvas);
		}
		super.draw(canvas);
	}

	@Override
	protected void handleDamageEvent(float damageTook) {}
	@Override
	protected void handleDeathDropEvent() {
		getOwnerArea().registerActor(new CastleKey(getOwnerArea(), Orientation.DOWN, getCurrentMainCellCoordinates()));
	}

	private class ARPGDarkLordHandler implements ARPGInteractionVisitor {
		
		@Override
		public void interactWith(ARPGPlayer player) {
			if (getCurrentState().equals(State.IDLE))
				setCurrentState(State.IS_GOING_TO_TELEPORT);
		}

	}
}
