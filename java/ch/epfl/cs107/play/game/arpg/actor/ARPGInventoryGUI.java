package ch.epfl.cs107.play.game.arpg.actor;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class ARPGInventoryGUI implements Graphics {
	
	private static final int GUI_DEPTH = 1000;
	
	public ARPGInventoryGUI() {
		
	}

	@Override
	public void draw(Canvas canvas) {
		float width = canvas.getScaledWidth();
		float height = canvas.getScaledHeight();
		Vector anchor = canvas.getTransform().getOrigin().sub(new Vector(width/2, height/2));
		
		//Background
		new ImageGraphics(ResourcePath.getSprite("zelda/inventory.background"),
				9.5f, 9.5f, new RegionOfInterest(0, 0, 240, 240),
				anchor.add(new Vector(3.f, 0.f)), 1, GUI_DEPTH).draw(canvas);
		
		//Title
		TextGraphics title = new TextGraphics("Inventory", 1.f, Color.BLACK);
		title.setDepth(GUI_DEPTH);
		title.setRelativeTransform(Transform.I.translated(canvas.getPosition().sub(width/2, height/2)));
		title.setAnchor(new Vector(5.25f, 8.f));
		title.draw(canvas);
		
		//Slots
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 4; j++) {
				new ImageGraphics(ResourcePath.getSprite("zelda/inventory.slot"),
						2.f, 2.f, new RegionOfInterest(0, 0, 64, 64),
						anchor.add(new Vector(2*j + 3.7f, 3.f*i + 2.f)), 1, GUI_DEPTH).draw(canvas);
			}
		}
	}

}
