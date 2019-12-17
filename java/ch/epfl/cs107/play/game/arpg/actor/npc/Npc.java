package ch.epfl.cs107.play.game.arpg.actor.npc;

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
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Npc extends MovableAreaEntity implements Interactor {
	
	private static final int IDLE_MOVEMENT_FRAMES = 20;
	private static final int TIME_NOT_MOVING = 20;
	
	private Sprite emotionSprite;
	private String textDialog;
	private Animation[] animations;
	private ARPGNpcHandler handler;
	private boolean canMove;
	private int counter;
	private boolean talked;
	
	public Npc(String textDialog, Emotion emotion, Area area, Orientation orientation, DiscreteCoordinates position) {
		super(area, orientation, position);
		
		this.textDialog = textDialog;
		handler = new ARPGNpcHandler();
		canMove = true;
		counter = 0;
		talked = false;
		
		Sprite[][] sprites = RPGSprite.extractSprites("zelda/character", 4, 1, 2, this , 16, 32, new Orientation[]
				{Orientation.UP , Orientation.RIGHT , Orientation.DOWN, Orientation.LEFT});
		animations = RPGSprite.createAnimations(10, sprites);
		
		emotionSprite = new RPGSprite("custom/npc.dialogBubble", 1.f, 1.f, this,
				new RegionOfInterest(emotion.getX(), emotion.getY(), 16, 16), new Vector(0.25f, 1.f));
	}
	
	public String getTextDialog() {
		return textDialog;
	}
	
	public void setOrientation(Orientation orientation) {
		orientate(orientation);
	}
	
	public void talked() {
		talked = true;
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
		if (!talked)
			emotionSprite.draw(canvas);
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

	private class ARPGNpcHandler implements ARPGInteractionVisitor {
		@Override
		public void interactWith(ARPGPlayer player) {
			canMove = false;
		}
	}
}
