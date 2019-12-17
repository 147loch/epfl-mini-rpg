package ch.epfl.cs107.play.game.arpg.actor.battle.monster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.arpg.actor.ARPGPlayer;
import ch.epfl.cs107.play.game.arpg.actor.Rarity;
import ch.epfl.cs107.play.game.arpg.actor.battle.DamageType;
import ch.epfl.cs107.play.game.arpg.actor.collectable.CastleKey;
import ch.epfl.cs107.play.game.arpg.actor.entity.FireSpell;
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
    private static final int RADIUS = 2;
    private static final int TELEPORTATION_RADIUS = 3;
    private static final int MIN_SPELL_WAIT_DURATION = 50;
    private static final int MAX_SPELL_WAIT_DURATION = 100;
    private static final double INVOKE_INSTEAD_TELEPORTATION = Rarity.UNCOMMON;
	
	private ARPGDarkLordHandler handler;
	private int initX;
	private int initY;
	private int updateCounter;
	
	private Animation[] animationsIdle;
	private Animation[] animationsAttack;
	private Animation animationIdle;
	private Animation animationAttack;
	private Animation animationVanishTeleportation;

	public DarkLord(Area area, Orientation orientation, DiscreteCoordinates coordinates) {
		super(area, orientation, coordinates, 2.f, VULNERABILITIES);
		
		handler = new ARPGDarkLordHandler();
		initX = (int)getPosition().x;
		initY = (int)getPosition().y;
		
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
		if (getOwnerArea().canEnterAreaCells(this, Collections.singletonList(new DiscreteCoordinates(x, y)))) {
			getOwnerArea().leaveAreaCells(this, getCurrentCells());
			setCurrentPosition(new Vector(x, y));
			getOwnerArea().enterAreaCells(this, Collections.singletonList(new DiscreteCoordinates(x, y)));
			setCurrentState(State.IDLE);
		} else {
			teleportation(initX + RandomGenerator.getInstance().nextInt(2 * TELEPORTATION_RADIUS) - TELEPORTATION_RADIUS,
					initY + RandomGenerator.getInstance().nextInt(2 * TELEPORTATION_RADIUS) - TELEPORTATION_RADIUS);
		}
	}
	
	private void invokeFlameSkull() {
		FlameSkull flameSkull = new FlameSkull(getOwnerArea(), getOrientation(), getCurrentMainCellCoordinates().jump(getOrientation().toVector().mul(2)));
		if (getOwnerArea().canEnterAreaCells(flameSkull, Collections.singletonList(getCurrentMainCellCoordinates().jump(getOrientation().toVector().mul(2))))) {
			getOwnerArea().registerActor(flameSkull);
			setCurrentState(State.IDLE);
		} else {
			orientate(Orientation.values()[RandomGenerator.getInstance().nextInt(4)]);
			invokeFlameSkull();
		}
	}

	@Override
	public void update(float deltaTime) {
		updateCounter++;
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
				
				if (updateCounter >= RandomGenerator.getInstance().nextInt(MAX_SPELL_WAIT_DURATION - MIN_SPELL_WAIT_DURATION) + MIN_SPELL_WAIT_DURATION) {
					updateCounter = 0;
					if (RandomGenerator.getInstance().nextDouble() >= INVOKE_INSTEAD_TELEPORTATION)
						setCurrentState(State.ATTACK);
					else
						setCurrentState(State.INVOKE);
				}
				break;
			case ATTACK:
				FireSpell fireSpell = new FireSpell(getOwnerArea(), getOrientation(),
						getCurrentMainCellCoordinates().jump(getOrientation().toVector().mul(2)), 4);
				if (getOwnerArea().canEnterAreaCells(fireSpell, Collections.singletonList(getCurrentMainCellCoordinates().jump(getOrientation().toVector().mul(2)))))
					getOwnerArea().registerActor(fireSpell);
				setCurrentState(State.IDLE);
				break;
			case INVOKE:
				invokeFlameSkull();
				break;
			case IS_GOING_TO_TELEPORT:
				animationAttack = animationsAttack[getOrientation().ordinal()];
				animationAttack.update(deltaTime);
				break;
			case TELEPORTATION:
				animationVanishTeleportation.update(deltaTime);
				if (animationVanishTeleportation.isCompleted())
					teleportation(initX + RandomGenerator.getInstance().nextInt(2 * TELEPORTATION_RADIUS) - TELEPORTATION_RADIUS,
							initY + RandomGenerator.getInstance().nextInt(2 * TELEPORTATION_RADIUS) - TELEPORTATION_RADIUS);
				break;
			default:
				break;
		}
		super.update(deltaTime);
	}
	
	@Override
	public List<DiscreteCoordinates> getFieldOfViewCells() {
		ArrayList<DiscreteCoordinates> fieldOfView = new ArrayList<>();
		for (int i = 0; i < 2 * RADIUS + 1; i++) {
			for (int j = 0; j < 2 * RADIUS + 1; j++) {
				fieldOfView.add(getCurrentMainCellCoordinates().jump(i - RADIUS, j - RADIUS));
			}
		}
		return Collections.unmodifiableList(fieldOfView);
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
		if (getCurrentState().equals(State.IDLE) || getCurrentState().equals(State.ATTACK)) {
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
