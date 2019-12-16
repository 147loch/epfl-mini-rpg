package ch.epfl.cs107.play.game.arpg.actor.entity;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.arpg.actor.battle.DamageType;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RandomGenerator;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.window.Canvas;

public class FireSpell extends AreaEntity {

	private static final DamageType DAMAGE_TYPE = DamageType.FIRE;
	private static final int MIN_LIFE_TIME = 120;
	private static final int MAX_LIFE_TIME = 240;
	private static final int PROPAGATION_TIME_FIRE = 20;
	private static final int ANIMATION_FRAME_LENGTH = 7;
	
	private int strength;
	private Animation animation;
	private int updateCounter;
	private int lifeTime;
	
	public FireSpell(Area area, Orientation orientation, DiscreteCoordinates position, int strength) {
		super(area, orientation, position);
		
		this.strength = strength;
		lifeTime = RandomGenerator.getInstance().nextInt(MAX_LIFE_TIME - MIN_LIFE_TIME) + MIN_LIFE_TIME;
		
		Sprite[] sprites = new Sprite[ANIMATION_FRAME_LENGTH];
		
		for (int i = 0; i < ANIMATION_FRAME_LENGTH; i++) {
			sprites[i] = new RPGSprite("zelda/fire", 1.f, 1.f, this, new RegionOfInterest(i*16, 0, 16, 16));
		}
		animation = new Animation(ANIMATION_FRAME_LENGTH / 3, sprites, true);
	}

	@Override
	public void update(float deltaTime) {
		updateCounter++;
		
		if (updateCounter >= lifeTime)
			getOwnerArea().unregisterActor(this);
		else if (updateCounter >= PROPAGATION_TIME_FIRE && strength > 0) {
			getOwnerArea().registerActor(new FireSpell(getOwnerArea(), getOrientation(),
					getCurrentMainCellCoordinates().jump(getOrientation().toVector()), strength - 1));
			strength = 0;
		}
		
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
		return false;
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		((ARPGInteractionVisitor)v).interactWith(this);
	}

	@Override
	public void draw(Canvas canvas) {
		animation.draw(canvas);
	}

}
