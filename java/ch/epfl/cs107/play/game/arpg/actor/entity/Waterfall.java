package ch.epfl.cs107.play.game.arpg.actor.entity;

import ch.epfl.cs107.play.game.actor.Entity;
import ch.epfl.cs107.play.game.actor.SoundAcoustics;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Audio;
import ch.epfl.cs107.play.window.Canvas;

public class Waterfall extends Entity implements SoundEntity {

	private static final int ANIMATION_FRAME_LENGTH = 3;
	
	private Animation animation;
	private SoundAcoustics sound;

	/**
	 * Constructor for the Waterfall
	 * @param position the position
	 */
	public Waterfall(Vector position) {
		super(position);

		sound = new SoundAcoustics(ResourcePath.getSounds("custom/waterfall"), 0.15f, true, false, true, false);

		Sprite[] sprites = new Sprite[ANIMATION_FRAME_LENGTH];
		for (int i = 0; i < ANIMATION_FRAME_LENGTH; i++) {
			sprites[i] = new RPGSprite("zelda/waterfall", 4.f, 4.f, this, new RegionOfInterest((i*64), 0, 64, 64));
		}
		animation = new Animation(sprites.length, sprites, true);
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

	@Override
	public void bip(Audio audio) {
		sound.bip(audio);
	}

	@Override
	public void reactivateSounds() {
		sound.shouldBeStarted();
	}
}
