package ch.epfl.cs107.play.game.arpg.actor;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.Positionable;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class ARPGPlayerStatusGUI implements Graphics, Positionable {

	private static final int DEPTH = 1000;
	
	private Sprite[] spritesHeart;
	private Sprite[] spritesDigit;

	protected ARPGPlayerStatusGUI() {
		for (int i = 0; i < 3; i++) {
			spritesHeart[i] = new RPGSprite("zelda/heartDisplay", 1.f, 1.f, this, new RegionOfInterest(i*16, 0, 16, 16));
		}
	}
	
	@Override
	public void draw(Canvas canvas) {
		float width = canvas.getScaledWidth();
		float height = canvas.getScaledHeight();
		Vector anchor = canvas.getTransform().getOrigin().sub(new Vector(width/2, height/2));
		
		//DISPLAY GEAR
		ImageGraphics gearDisplay = new ImageGraphics(ResourcePath.getSprite("zelda/gearDisplay"),
				1.5f, 1.5f, new RegionOfInterest(0, 0, 32, 32),
				anchor.add(new Vector(0.25f, height - 1.75f)), 1, DEPTH);
		gearDisplay.draw(canvas);
	}

	@Override
	public Transform getTransform() {
		return null;
	}

	@Override
	public Vector getVelocity() {
		return null;
	}
}
