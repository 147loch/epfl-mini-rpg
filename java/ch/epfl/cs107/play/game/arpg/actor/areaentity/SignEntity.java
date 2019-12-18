package ch.epfl.cs107.play.game.arpg.actor.areaentity;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.game.rpg.actor.Sign;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.window.Canvas;

public class SignEntity extends Sign {

	private RPGSprite sprite;
	
	/**
	 * Constructor for the SignEntity
	 * @param textMessage the message of the dialog
	 * @param withGrass true, for the sign with grass at the bottom of the sign
	 * @param area the area
	 * @param orientation the orientation
	 * @param position the position in the area
	 */
	public SignEntity(String textMessage, boolean withGrass, Area area, Orientation orientation, DiscreteCoordinates position) {
		super(textMessage, area, orientation, position);
		
		int type = withGrass ? 0 : 1;
		sprite = new RPGSprite("custom/signs", 1.f, 1.f, this, new RegionOfInterest((type*16), 0, 16, 16));
	}

	@Override
	public void draw(Canvas canvas) {
		sprite.draw(canvas);
	}
}
