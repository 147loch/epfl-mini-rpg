package ch.epfl.cs107.play.game.arpg.actor;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.window.Canvas;

public class Grass extends AreaEntity {

	private Sprite sprite;
	private Animation animation;
	
	private boolean active;
	
	public Grass(Area area, Orientation orientation, DiscreteCoordinates position) {
		super(area, orientation, position);
		sprite = new Sprite("zelda/grass", 1.f, 1.f, this, new RegionOfInterest(0, 0, 16, 16));
		active = true;
		
		Sprite[] sprites = {null, null, null , null};
		
		for (int i = 0; i < 4; i++) {
			sprites[i] = new RPGSprite("zelda/grass.sliced", 1.f, 1.f, this, new RegionOfInterest(i, 0, 32, 32));
		}
		animation = new Animation(4, sprites);
	}
	
	@Override
	public void update(float deltaTime) {
		
		if (!active)
			animation.update(deltaTime);
		
		super.update(deltaTime);
	}

	public void setInactive() {
		active = false;
	}
	
	@Override
	public List<DiscreteCoordinates> getCurrentCells() {
		return Collections.singletonList(getCurrentMainCellCoordinates());
	}

	@Override
	public boolean takeCellSpace() {
		return active;
	}

	@Override
	public boolean isCellInteractable() {
		return false;
	}

	@Override
	public boolean isViewInteractable() {
		return true;
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
        ((ARPGInteractionVisitor)v).interactWith(this);
    }

	@Override
	public void draw(Canvas canvas) {
		if (active)
			sprite.draw(canvas);
		else
			animation.draw(canvas);
	}

}
