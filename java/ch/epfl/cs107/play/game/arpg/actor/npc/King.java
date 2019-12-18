package ch.epfl.cs107.play.game.arpg.actor.npc;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.game.rpg.actor.Sign;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public class King extends Sign {

	private Animation[] animation;
	
	/**
	 * Constructor for the King
	 * @param area the area
	 * @param orientation the orientation
	 * @param position the position in the area
	 */
	public King(Area area, Orientation orientation, DiscreteCoordinates position) {
		super("Merci beaucoup aventurier !", area, orientation, position);
		
		Sprite[][] sprites = RPGSprite.extractSprites("zelda/king", 1, 1, 2, this , 16, 32, new Orientation[]
				{Orientation.UP , Orientation.RIGHT , Orientation.DOWN, Orientation.LEFT});
		animation = RPGSprite.createAnimations(0, sprites);
	}
	
	/**
	 * This method is used to change the orientation of the King
	 * @param orientation the orientation
	 */
	public void setOrientation(Orientation orientation) {
		orientate(orientation);
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		((ARPGInteractionVisitor)v).interactWith(this);
	}

	@Override
	public void draw(Canvas canvas) {
		animation[getOrientation().ordinal()].draw(canvas);
	}

}
