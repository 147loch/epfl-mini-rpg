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
		new ImageGraphics(ResourcePath.getSprite("zelda/gearDisplay"),
				1.5f, 1.5f, new RegionOfInterest(0, 0, 32, 32),
				anchor.add(new Vector(0.25f, height - 1.75f)), 1, DEPTH).draw(canvas);
		
		//DISPLAY CURRENT ITEM
		if (player.getCurrentItem() != null) {
			new ImageGraphics(player.getCurrentItem().getResourcePath()	,
					1.f, 1.f, new RegionOfInterest(0, 0, 16, 16),
					anchor.add(new Vector(0.5f, height - 1.5f)), 1, DEPTH + 1).draw(canvas);
		}

		//DISPLAY COIN
		new ImageGraphics(ResourcePath.getSprite("zelda/coinsDisplay"),
				4.f, 2.f, new RegionOfInterest(0, 0, 64, 32),
				anchor.add(new Vector(0.25f, 0.25f)), 1, DEPTH).draw(canvas);
		
		//DISPLAY NUMBER FOR COINS
		for (int i = 0; i < Math.floor(Math.log10(player.getInventoryMoney()) + 1); i++) {
			int row = 2;
			int column = 3;

			int c = Character.digit(Integer.toString(player.getInventoryMoney()).charAt(i), 10);

			switch (Integer.toString(player.getInventoryMoney()).charAt(i)) {
				case ('1'):
					row = 0;
					column = 0;
					break;
				case ('2'):
					row = 1;
					column = 0;
					break;
				case ('3'):
					row = 2;
					column = 0;
					break;
				case ('4'):
					row = 3;
					column = 0;
					break;
				case ('5'):
					row = 0;
					column = 1;
					break;
				case ('6'):
					row = 1;
					column = 1;
					break;
				case ('7'):
					row = 2;
					column = 1;
					break;
				case ('8'):
					row = 3;
					column = 1;
					break;
				case ('9'):
					row = 0;
					column = 2;
					break;
				case ('0'):
					row = 1;
					column = 2;
					break;
			}
			
			new ImageGraphics(ResourcePath.getSprite("zelda/digits"),
					0.8f, 0.8f, new RegionOfInterest(row*16, column*16, 16, 16),
					anchor.add(new Vector(2f + i*0.5f, 0.9f)), 1, DEPTH + 1).draw(canvas);
		}
		
		//DISPLAY HEART
		for (int i = 0; i < 5; i++) {
			ImageGraphics graphicHeart;
			int index;
			if (player.getHp() < (i + 0.5f)) { //NO LIFE
				index = 0;
			} else if (player.getHp() == (i + 0.5f)) { //MIDLIFE
				index = 16;
			} else { //FULL LIFE
				index = 32;
			}
			new ImageGraphics(ResourcePath.getSprite("zelda/heartDisplay"),
					1.f, 1.f, new RegionOfInterest(index, 0, 16, 16),
					anchor.add(new Vector(2f + i*1.f, height - 1.f)), 1, DEPTH).draw(canvas);
		}
	}
}
