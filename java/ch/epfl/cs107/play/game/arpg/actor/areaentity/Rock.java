package ch.epfl.cs107.play.game.arpg.actor.areaentity;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.window.Canvas;

public class Rock extends AreaEntity {

	private RPGSprite sprite;
	
	/**
	 * Constructor for the Rock
	 * @param type the type of rock, between 0 and 4
	 * @param area the area
	 * @param position the position in the area
	 */
	public Rock(int type, Area area, DiscreteCoordinates position) {
		super(area, Orientation.DOWN, position);
		if (type < 0 || type > 4)
			type = 0;
		sprite = new RPGSprite("custom/rocks", 1.f, 1.f, this, new RegionOfInterest((type*16), 0, 16, 16));
	}

	@Override
	public void draw(Canvas canvas) {
		sprite.draw(canvas);
	}

	@Override
	public boolean takeCellSpace() {
		return true;
	}

	@Override
	public List<DiscreteCoordinates> getCurrentCells() {
		return Collections.singletonList(getCurrentMainCellCoordinates());
	}

	@Override
	public boolean isCellInteractable() {
		return false;
	}

	@Override
	public boolean isViewInteractable() {
		return false;
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		((ARPGInteractionVisitor)v).interactWith(this);
	}
}
