package ch.epfl.cs107.play.game.arpg.actor;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class ARPGInventoryGUI implements Graphics {
	
	private static final int GUI_DEPTH = 1000;

	@Override
	public void draw(Canvas canvas) {
		float width = canvas.getScaledWidth();
		float height = canvas.getScaledHeight();
		Vector anchor = canvas.getTransform().getOrigin().sub(new Vector(width/2, height/2));
		
		new ImageGraphics(ResourcePath.getSprite("zelda/inventory.background"),
				9.5f, 9.5f, new RegionOfInterest(0, 0, 240, 240),
				anchor.add(new Vector(3.f, 0.f)), 1, GUI_DEPTH).draw(canvas);;
		
		TextGraphics title = new TextGraphics("Inventory", 2.f, Color.BLACK);
		title.draw(canvas);
		
		new ImageGraphics(ResourcePath.getSprite("zelda/inventory.slot"),
				2.f, 2.f, new RegionOfInterest(0, 0, 64, 64),
				anchor.add(new Vector(3.f, 0.f)), 1, GUI_DEPTH).draw(canvas);
	}

}
