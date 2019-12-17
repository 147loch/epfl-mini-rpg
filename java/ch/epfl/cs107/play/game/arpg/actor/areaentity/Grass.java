package ch.epfl.cs107.play.game.arpg.actor.areaentity;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.arpg.actor.Rarity;
import ch.epfl.cs107.play.game.arpg.actor.collectable.Coin;
import ch.epfl.cs107.play.game.arpg.actor.collectable.Heart;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RandomGenerator;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.window.Canvas;

public class Grass extends AreaEntity {

	private final static int ANIMATION_FRAME_LENGTH = 4;
	
	private final static double PROBABILITY_TO_DROP_ITEM = Rarity.UNCOMMON;
	private final static double PROBABIBITY_TO_DROP_HEART = Rarity.COMMON;
	
	private Sprite sprite;
	private Animation animation;

	private boolean active;
	private boolean dropped;
	
	public Grass(Area area, Orientation orientation, DiscreteCoordinates position) {
		super(area, orientation, position);
		sprite = new RPGSprite("zelda/grass", 1.f, 1.f, this, new RegionOfInterest(0, 0, 16, 16));
		active = true;
		dropped = false;
		
		Sprite[] sprites = new Sprite[ANIMATION_FRAME_LENGTH];
		
		for (int i = 0; i < ANIMATION_FRAME_LENGTH; i++) {
			sprites[i] = new RPGSprite("zelda/grass.sliced", 2.f, 2.f, this, new RegionOfInterest((i*32) + 5, 0, 32, 32));
		}
		animation = new Animation(sprites.length, sprites, false);
	}
	
	@Override
	public void update(float deltaTime) {
		
		if (!active)
			animation.update(deltaTime);
		
		super.update(deltaTime);
	}

	public void cut(boolean wantDroppingItems) {
		active = false;
		
		if (wantDroppingItems && !dropped) {
			if (RandomGenerator.getInstance().nextDouble() < PROBABILITY_TO_DROP_ITEM) {
				if (RandomGenerator.getInstance().nextDouble() < PROBABIBITY_TO_DROP_HEART) {
					getOwnerArea().registerActor(new Heart(getOwnerArea(), Orientation.UP,
							new DiscreteCoordinates((int)this.getPosition().x, (int)this.getPosition().y)));
				} else {
					getOwnerArea().registerActor(new Coin(getOwnerArea(), Orientation.UP,
							new DiscreteCoordinates((int)this.getPosition().x, (int)this.getPosition().y)));
				}
			}
			dropped = true;
		}
	}
	
	@Override
	public List<DiscreteCoordinates> getCurrentCells() {
		return Collections.singletonList(getCurrentMainCellCoordinates());
	}

	@Override
	public boolean takeCellSpace() {
		return active;
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
		if (active)
			sprite.draw(canvas);
		else if (!animation.isCompleted()) {
			animation.draw(canvas);
		} else {
			getOwnerArea().unregisterActor(this);
		}
	}

}
