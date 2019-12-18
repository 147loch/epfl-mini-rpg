package ch.epfl.cs107.play.game.arpg.actor.entity;

import ch.epfl.cs107.play.game.actor.Entity;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class WaterEffect extends Entity {

	private static final int ANIMATION_FRAME_LENGTH = 8;
	private static final int ANIMATION_SPEED = 5;
	
	private Animation animation;
	
	/**
	 * COnstructor for the WaterEffect
	 * @param position the position
	 */
	public WaterEffect(Vector position) {
		super(position);
		
		Sprite[] sprites = new Sprite[ANIMATION_FRAME_LENGTH];
		for (int i = 0; i < ANIMATION_FRAME_LENGTH; i++) {
			sprites[i] = new RPGSprite("custom/water.effects", 1.f, 1.f, this, new RegionOfInterest(i*16, 0, 16, 16));
		}
		animation = new Animation(ANIMATION_SPEED, sprites, true);
	}

	@Override
	public void update(float deltaTime) {
		animation.update(deltaTime);
		super.update(deltaTime);
	}
	
	@Override
	public void draw(Canvas canvas) {
		animation.draw(canvas);
	}

}
