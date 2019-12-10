package ch.epfl.cs107.play.game.arpg.actor;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class ARPGPlayerStatusGUI implements Graphics {

	private static final int GUI_DEPTH = 1000;
	private static final float INV_HEART_TIMER = 0.25f;
	private static final float INV_HEART_TIMER_DECREASE_INTERVAL = 0.05f;

	private ARPGPlayer player;
	private boolean isInvHeartOff = true;
	private float invHeartTimer = INV_HEART_TIMER;
	private int blinkCounter = 0;

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
				anchor.add(new Vector(0.25f, height - 1.75f)), 1, GUI_DEPTH).draw(canvas);
		
		//DISPLAY CURRENT ITEM
		if (player.getCurrentItem() != null) {
			new ImageGraphics(player.getCurrentItem().getResourcePath()	,
					1.f, 1.f, new RegionOfInterest(0, 0, 16, 16),
					anchor.add(new Vector(0.5f, height - 1.5f)), 1, GUI_DEPTH + 1).draw(canvas);
		}

		//DISPLAY COIN
		new ImageGraphics(ResourcePath.getSprite("zelda/coinsDisplay"),
				4.f, 2.f, new RegionOfInterest(0, 0, 64, 32),
				anchor.add(new Vector(0.5f, 0.25f)), 1, GUI_DEPTH).draw(canvas);
		
		//DISPLAY NUMBER FOR COINS
		for (int i = 0; i < Math.floor(Math.log10(player.getInventoryMoney()) + 1); i++) {
			int row = 2;
			int column = 1;
			int c = Character.digit(Integer.toString(player.getInventoryMoney()).charAt(i), 10);
			if (c != 0) {
				column = (c - 1) % 4;
				row = (c - 1) / 4;
			}
			
			new ImageGraphics(ResourcePath.getSprite("zelda/digits"),
					0.8f, 0.8f, new RegionOfInterest(column*16, row*16, 16, 16),
					anchor.add(new Vector(2.25f + i*0.5f, 0.9f)), 1, GUI_DEPTH + 1).draw(canvas);
		}
		
		//DISPLAY HEART
		// TODO replace hardcoded values with player constant for how much damage he can take
		for (int i = 0; i < Math.ceil(player.getMaxHp()); i++) {
			int index;

			if (player.getHp() < (i + 0.5f)) { //NO LIFE
				index = 0;
			} else if (player.getHp() == (i + 0.5f)) { //MIDLIFE
				index = 16;
			} else { //FULL LIFE
				index = 32;
			}

			if (!player.tookDamage() && invHeartTimer < INV_HEART_TIMER)
				invHeartTimer = INV_HEART_TIMER;

			if (!player.tookDamage() && !isInvHeartOff)
				isInvHeartOff = true;

			if (!player.tookDamage() && blinkCounter >= 3)
				blinkCounter = 0;

			if (player.tookDamage() && i == Math.floor(player.getHp()) && !isInvHeartOff && blinkCounter <= 3) {
				int blinkIndex;
				if ((player.getHp() + 0.5) % 1 != 0) { //MIDLIFE
					blinkIndex = 16;
				} else { //FULL LIFE
					blinkIndex = 32;
				}

				new ImageGraphics(ResourcePath.getSprite("zelda/heartDisplay"),
						1.f, 1.f, new RegionOfInterest(blinkIndex, 0, 16, 16),
						anchor.add(new Vector(2f + i*1.f, height - 1.5f)), 1, GUI_DEPTH).draw(canvas);

				if (invHeartTimer > 0) {
					isInvHeartOff = false;
					invHeartTimer -= INV_HEART_TIMER_DECREASE_INTERVAL;
				} else {
					invHeartTimer = INV_HEART_TIMER;
					isInvHeartOff = true;
					blinkCounter++;
				}
			} else {
				new ImageGraphics(ResourcePath.getSprite("zelda/heartDisplay"),
					1.f, 1.f, new RegionOfInterest(index, 0, 16, 16),
					anchor.add(new Vector(2f + i*1.f, height - 1.5f)), 1, GUI_DEPTH).draw(canvas);

				if (i == Math.floor(player.getHp()) && isInvHeartOff && player.tookDamage()) {
					if (invHeartTimer > 0) {
						invHeartTimer -= INV_HEART_TIMER_DECREASE_INTERVAL;
					} else {
						invHeartTimer = INV_HEART_TIMER;
						isInvHeartOff = false;
						blinkCounter++;
					}
				}
			}
		}
	}
}
