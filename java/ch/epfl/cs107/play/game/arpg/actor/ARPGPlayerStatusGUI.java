package ch.epfl.cs107.play.game.arpg.actor;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.util.ArrayList;
import java.util.List;

public class ARPGPlayerStatusGUI implements Graphics {

	private static final int GUI_DEPTH = 1000;
	private static final float INV_HEART_TIMER = 0.25f;
	private static final float INV_HEART_TIMER_DECREASE_INTERVAL = 0.05f;
	private static final int INV_HEART_BLINK_TIMES = 3;

	private ARPGPlayer player;

	private boolean isInvHeartOff = true;
	private float invHeartTimer = INV_HEART_TIMER;
	private int blinkCounter = 0;

	protected ARPGPlayerStatusGUI(ARPGPlayer player) {
		this.player = player;
	}
	
	@Override
	public void draw(Canvas canvas) {
		List<Graphics> arrayHeartsBeforeDamage = new ArrayList<>();
		List<Graphics> arrayHearts = new ArrayList<>();

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
		if (player.tookDamage() >= 0) {
			for (int i = 0; i < Math.ceil(player.getMaxHp()); i++) {
				int index;

				if (player.getHp() + player.tookDamage() < (i + 0.5f)) { //NO LIFE
					index = 0;
				} else if (player.getHp() + player.tookDamage() == (i + 0.5f)) { //MIDLIFE
					index = 16;
				} else { //FULL LIFE
					index = 32;
				}

				arrayHeartsBeforeDamage.add(new ImageGraphics(ResourcePath.getSprite("zelda/heartDisplay"),
						1.f, 1.f, new RegionOfInterest(index, 0, 16, 16),
						anchor.add(new Vector(2f + i*1.f, height - 1.5f)), 1, GUI_DEPTH));
			}
		}

		for (int i = 0; i < Math.ceil(player.getMaxHp()); i++) {
			int index;

			if (player.getHp() < (i + 0.5f)) { //NO LIFE
				index = 0;
			} else if (player.getHp() == (i + 0.5f)) { //MIDLIFE
				index = 16;
			} else { //FULL LIFE
				index = 32;
			}

			arrayHearts.add(new ImageGraphics(ResourcePath.getSprite("zelda/heartDisplay"),
					1.f, 1.f, new RegionOfInterest(index, 0, 16, 16),
					anchor.add(new Vector(2f + i*1.f, height - 1.5f)), 1, GUI_DEPTH));
		}

		if (player.tookDamage() <= 0 && invHeartTimer < INV_HEART_TIMER)
			invHeartTimer = INV_HEART_TIMER;

		if (player.tookDamage() <= 0 && blinkCounter != 0)
			blinkCounter = 0;

		if (player.tookDamage() >= 0 && !isInvHeartOff && blinkCounter <= INV_HEART_BLINK_TIMES) {
			arrayHeartsBeforeDamage.forEach(heart -> heart.draw(canvas));

			if (invHeartTimer > 0) {
				isInvHeartOff = false;
				invHeartTimer -= INV_HEART_TIMER_DECREASE_INTERVAL;
			} else {
				invHeartTimer = INV_HEART_TIMER;
				isInvHeartOff = true;
				blinkCounter++;
			}
		} else {
			arrayHearts.forEach(heart -> heart.draw(canvas));

			if (isInvHeartOff && player.tookDamage() >= 0) {
				if (invHeartTimer > 0) {
					invHeartTimer -= INV_HEART_TIMER_DECREASE_INTERVAL;
				} else {
					invHeartTimer = INV_HEART_TIMER;
					isInvHeartOff = false;
					blinkCounter++;
				}
			} else {
				isInvHeartOff = true;
			}
		}
	}
}
