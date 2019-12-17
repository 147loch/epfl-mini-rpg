package ch.epfl.cs107.play.game.arpg.actor.sign;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.arpg.actor.ARPGPlayer;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RandomGenerator;
import ch.epfl.cs107.play.window.Canvas;

public class Pnj extends MovableAreaEntity implements Interactor {
	
	private static final int IDLE_MOVEMENT_FRAMES = 20;
	private static final int TIME_NOT_MOVING = 20;
	
	private String textDialog;
	private Animation[] animations;
	private ARPGPnjHandler handler;
	private boolean canMove;
	private int counter;
	
	public Pnj(String textDialog, Area area, Orientation orientation, DiscreteCoordinates position) {
		super(area, orientation, position);
		
		this.textDialog = textDialog;
		handler = new ARPGPnjHandler();
		canMove = true;
		counter = 0;
		
		Sprite[][] sprites = RPGSprite.extractSprites("zelda/character", 4, 1, 2, this , 16, 32, new Orientation[]
				{Orientation.UP , Orientation.RIGHT , Orientation.DOWN, Orientation.LEFT});
		animations = RPGSprite.createAnimations(10, sprites);
	}
	
	public String getTextDialog() {
		return textDialog;
	}
	
	public void setOrientation(Orientation orientation) {
		orientate(orientation);
	}
	
	@Override
	public void update(float deltaTime) {
		if (canMove) {
			animations[getOrientation().ordinal()].update(deltaTime);
			if (!isDisplacementOccurs())
			{
	            if (RandomGenerator.getInstance().nextDouble() > 0.3) {
	                resetMotion();
	                orientate(Orientation.fromInt(RandomGenerator.getInstance().nextInt(4)));
	            } else {
	                move(IDLE_MOVEMENT_FRAMES);
	            }
			}
        } else {
        	animations[getOrientation().ordinal()].reset();
        	counter++;
        	
        	if (counter >= TIME_NOT_MOVING) {
        		counter = 0;
        		canMove = true;
        	}
        }
		
		super.update(deltaTime);
	}

	@Override
	public List<DiscreteCoordinates> getCurrentCells() {
		return Collections.singletonList(getCurrentMainCellCoordinates());
	}

	@Override
	public boolean takeCellSpace() {
		return true;
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
		animations[getOrientation().ordinal()].draw(canvas);
	}

	@Override
	public List<DiscreteCoordinates> getFieldOfViewCells() {
		return getCurrentMainCellCoordinates().getNeighbours();
	}

	@Override
	public boolean wantsCellInteraction() {
		return false;
	}

	@Override
	public boolean wantsViewInteraction() {
		return true;
	}

	@Override
	public void interactWith(Interactable other) {
		other.acceptInteraction(handler);
	}

	private class ARPGPnjHandler implements ARPGInteractionVisitor {
		@Override
		public void interactWith(ARPGPlayer player) {
			canMove = false;
		}
	}
}
