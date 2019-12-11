package ch.epfl.cs107.play.game.arpg.actor;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.window.Canvas;

public class Arrow extends MovableAreaEntity {
	
	private static final int SPEED = 5;
	
	private RPGSprite[] sprites;

	public Arrow(Area area, Orientation orientation, DiscreteCoordinates position) {
		super(area, orientation, position);
		
		sprites = new RPGSprite[4];
		for (int i = 0; i < 4; i++) {
			sprites[i] = new RPGSprite("zelda/arrow", 1.f, 1.f, this, new RegionOfInterest(32*i, 0, 32, 32));	
		}
	}

	@Override
	public void update(float deltaTime) {
		move(SPEED);
		
		if (!isDisplacementOccurs()) //TODO supprimer aussi quand il atteint une distance max
			getOwnerArea().unregisterActor(this);
		
		super.update(deltaTime);
	}
	
	@Override
	public List<DiscreteCoordinates> getCurrentCells() {
		return Collections.singletonList(getCurrentMainCellCoordinates());
	}

	@Override
	public boolean takeCellSpace() {
		return false;
	}

	@Override
	public boolean isCellInteractable() {
		return true;
	}

	@Override
	public boolean isViewInteractable() {
		return false;
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		((ARPGInteractionVisitor)v).interactWith(this);	
	}

	@Override
	public void draw(Canvas canvas) {
		if (getOrientation().equals(Orientation.DOWN))
			sprites[2].draw(canvas);
		else if (getOrientation().equals(Orientation.UP))
			sprites[0].draw(canvas);
		else if (getOrientation().equals(Orientation.RIGHT))
			sprites[1].draw(canvas);
		else
			sprites[3].draw(canvas);
	}

}
