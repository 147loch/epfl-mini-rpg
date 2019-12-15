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
import ch.epfl.cs107.play.game.arpg.actor.battle.DamageType;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class DarkLord extends MonsterEntity {
	
	private static final DamageType[] VULNERABILITIES = { DamageType.MAGICAL };
	private static final int ANIMATION_DURATION = 4;
	
	private ARPGDarkLordHandler handler;
	
	private Animation[] animationsIdle;
	private Animation currentAnimation;

	public DarkLord(Area area, Orientation orientation, DiscreteCoordinates coordinates) {
		super(area, orientation, coordinates, 5.f, VULNERABILITIES);
		
		handler = new ARPGDarkLordHandler();
		
		Sprite[][] sprites = RPGSprite.extractSprites(
				"zelda/darkLord", 3, 2, 2, this ,
				32, 32, new Vector(-0.5f, 0),
				new Orientation[] {Orientation.UP , Orientation.LEFT , Orientation.DOWN, Orientation.RIGHT}
			);
		animationsIdle = RPGSprite.createAnimations(ANIMATION_DURATION, sprites);

		currentAnimation = animationsIdle[orientation.ordinal()];
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
		return false;
	}

	@Override
	public void interactWith(Interactable other) {
		other.acceptInteraction(handler);
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		((ARPGInteractionVisitor)v).interactWith(this);
	}
	
	@Override
	public void draw(Canvas canvas) {
		if (!State.DEAD.equals(getCurrentState()) && !currentAnimation.isCompleted()) {
			currentAnimation.draw(canvas);
		}
		super.draw(canvas);
	}

	@Override
	protected void handleDamageEvent(float damageTook) {}
	@Override
	protected void handleDeathDropEvent() {}

	private class ARPGDarkLordHandler implements ARPGInteractionVisitor {
		
		@Override
		public void interactWith(ARPGPlayer player) {
			
		}
	}
}
