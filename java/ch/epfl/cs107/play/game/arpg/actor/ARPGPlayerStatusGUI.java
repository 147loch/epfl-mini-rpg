package ch.epfl.cs107.play.game.arpg.actor;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class ARPGPlayerStatusGUI implements Graphics {

	private static final int DEPTH = 1000;
	
	private ARPGPlayer player;

	protected ARPGPlayerStatusGUI(ARPGPlayer player) {
		this.player = player;
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
		
		//DISPLAY HEART
		for (int i = 0; i < 5; i++) {
			ImageGraphics graphicHeart;
			if (player.getHp() < (i + 1) && player.getHp() < (i)) { //NO LIFE
				graphicHeart = new ImageGraphics(ResourcePath.getSprite("zelda/heartDisplay"),
						1.f, 1.f, new RegionOfInterest(0, 0, 16, 16),
						anchor.add(new Vector(2f + i*1.f, height - 1.f)), 1, DEPTH);
			} else if (player.getHp() < (i + 1) && player.getHp() > (i)) { //MIDLIFE
				graphicHeart = new ImageGraphics(ResourcePath.getSprite("zelda/heartDisplay"),
						1.f, 1.f, new RegionOfInterest(16, 0, 16, 16),
						anchor.add(new Vector(2f + i*1.f, height - 1.f)), 1, DEPTH);
			} else { //FULL LIFE
				graphicHeart = new ImageGraphics(ResourcePath.getSprite("zelda/heartDisplay"),
						1.f, 1.f, new RegionOfInterest(32, 0, 16, 16),
						anchor.add(new Vector(2f + i*1.f, height - 1.f)), 1, DEPTH);
			}
			graphicHeart.draw(canvas);
		}
	}
}
