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
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public abstract class CollectableAreaEntity extends AreaEntity {

	private Animation animation;
	
	private static final int ANIMATION_FRAME_LENGTH = 4;

	/**
	 * Create a Collectable Area Entity item
	 * @param area The area it is in
	 * @param orientation The orientation it should have
	 * @param position The position where it should appear
	 * @param animationString The animation sprite, not null
	 */
	public CollectableAreaEntity(Area area, Orientation orientation, DiscreteCoordinates position, String animationString) {
		this(area, orientation, position, animationString, ANIMATION_FRAME_LENGTH);
	}

	/**
	 * Create a Collectable Area Entity item
	 * @param area The area it is in
	 * @param orientation The orientation it should have
	 * @param position The position where it should appear
	 * @param animationString The animation sprite, not null
	 * @param animationLength The animation length, if not 4
	 */
	public CollectableAreaEntity(Area area, Orientation orientation, DiscreteCoordinates position, String animationString, int animationLength) {
		this(area, orientation, position, animationString, animationLength, 16);
	}

	/**
	 * Create a Collectable Area Entity item
	 * @param area The area it is in
	 * @param orientation The orientation it should have
	 * @param position The position where it should appear
	 * @param animationString The animation sprite, not null
	 * @param animationLength The animation length, if not 4
	 * @param animationSpriteSize One animation sprite size
	 */
	public CollectableAreaEntity(Area area, Orientation orientation, DiscreteCoordinates position, String animationString, int animationLength, int animationSpriteSize) {
		this(area, orientation, position, animationString, animationLength, animationSpriteSize, 1.f, new Vector(0.f, 0.f));
	}

	/**
	 * Create a Collectable Area Entity item
	 * @param area The area it is in
	 * @param orientation The orientation it should have
	 * @param position The position where it should appear
	 * @param animationString The animation sprite, not null
	 * @param animationLength The animation length, if not 4
	 * @param animationSpriteSize One animation sprite size
	 * @param finalAnimationSize The final animation scale
	 * @param anchor The anchor if needed
	 */
	public CollectableAreaEntity(Area area, Orientation orientation, DiscreteCoordinates position, String animationString, int animationLength, int animationSpriteSize, float finalAnimationSize, Vector anchor) {
		super(area, orientation, position);

		Sprite[] sprites = new Sprite[animationLength];
		for (int i = 0; i < animationLength; i++) {
			sprites[i] = new RPGSprite(animationString, finalAnimationSize, finalAnimationSize, this,
					new RegionOfInterest(i*animationSpriteSize, 0, animationSpriteSize, animationSpriteSize), anchor);
		}
		animation = new Animation(sprites.length, sprites, true);
	}

	// Default Overrides for subclasses

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
