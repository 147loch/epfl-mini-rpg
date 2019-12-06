package ch.epfl.cs107.play.game.arpg.actor.collectable;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.window.Canvas;

public abstract class CollectableAreaEntity extends AreaEntity {

	private Animation animation;
	
	private static final int ANIMATION_FRAME_LENGTH = 4;
	
	public CollectableAreaEntity(Area area, Orientation orientation, DiscreteCoordinates position, String animationString) {
		super(area, orientation, position);
		
		Sprite[] sprites = new Sprite[ANIMATION_FRAME_LENGTH];
	
		for (int i = 0; i < ANIMATION_FRAME_LENGTH; i++) {
			sprites[i] = new RPGSprite(animationString, 1.f, 1.f, this, new RegionOfInterest(i*16, 0, 16, 16));
		}
		animation = new Animation(sprites.length, sprites, true);
	}
	
	@Override
	public void update(float deltaTime) {
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
	public void draw(Canvas canvas) {
		animation.draw(canvas);
	}

}
