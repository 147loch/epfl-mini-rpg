package ch.epfl.cs107.play.game.arpg.actor.monster;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.arpg.actor.Grass;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class FlameSkull extends MonsterEntity {

	private final static int ANIMATION_DURATION = 4;
	
	private Animation[] animations;
	
	private ARPGFlameSkullHandler handler;
	
	public FlameSkull(Area area, Orientation orientation, DiscreteCoordinates position) {
		super(area, orientation, position, 2.f);
		
		Sprite[][] sprites = RPGSprite.extractSprites("zelda/flameSkull", 3, 2, 2, this , 32, 32, new Orientation[]
				{Orientation.DOWN , Orientation.RIGHT , Orientation.UP, Orientation.LEFT});
		animations = RPGSprite.createAnimations(ANIMATION_DURATION, sprites);
		for (int i = 0; i < 4; i++)
		{
			animations[i].setAnchor(new Vector(this.getTransform().getX().getY() - 0.5f, this.getTransform().getX().getY()));
		}
		setAnimation(animations[0]);
		
		handler = new ARPGFlameSkullHandler();
		
	}
	
	@Override
	public void update(float deltaTime) {
		
		if (this.getOrientation() == Orientation.UP) {
			setAnimation(animations[2]);
		} else if (this.getOrientation() == Orientation.DOWN) {
			setAnimation(animations[0]);
		} else if (this.getOrientation() == Orientation.RIGHT) {
			setAnimation(animations[3]);
		} else {
			setAnimation(animations[1]);
		}
		
		super.update(deltaTime);
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		((ARPGInteractionVisitor)v).interactWith(this);
	}
	
	@Override
	public void interactWith(Interactable other) {
		other.acceptInteraction(handler);
	}
	
	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
	}


	@Override
	public List<DiscreteCoordinates> getFieldOfViewCells() {
		return Collections.singletonList(getCurrentMainCellCoordinates().jump(getOrientation().toVector()));
	}

	@Override
	public boolean wantsCellInteraction() {
		return false;
	}

	@Override
	public boolean wantsViewInteraction() {
		return true;
	}
	
	private class ARPGFlameSkullHandler implements ARPGInteractionVisitor {
		@Override
		public void interactWith(Grass grass) {
			grass.cut(false);
		}
	}
}
