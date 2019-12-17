package ch.epfl.cs107.play.game.arpg.actor.entity;

import ch.epfl.cs107.play.game.actor.Entity;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Lilypads extends Entity {

	private RPGSprite sprite;
	
	public Lilypads(Vector position, int type) {
		super(position);
		if (type < 0 || type > 3)
			type = 0;
		sprite = new RPGSprite("custom/lilypads", 1.f, 1.f, this, new RegionOfInterest((type*16), 0, 16, 16));
	}

	@Override
	public void draw(Canvas canvas) {
		sprite.draw(canvas);
	}

}
