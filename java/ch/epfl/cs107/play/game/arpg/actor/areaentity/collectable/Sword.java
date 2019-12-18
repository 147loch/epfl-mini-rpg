package ch.epfl.cs107.play.game.arpg.actor.areaentity.collectable;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.window.Canvas;

public class Sword extends CollectableAreaEntity {

    private boolean isTaken;

    private Sprite normalSprite;
    private Sprite takenSprite;

    /**
     * Constructor for the Sword
     * @param area the area
     * @param orientation the orientation
     * @param position the position in the area
     */
	public Sword(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
        isTaken = false;

        normalSprite = new RPGSprite("custom/rock.sword", 1.f, 2.f, this, new RegionOfInterest(0, 0, 16, 32));
        takenSprite = new RPGSprite("custom/rock.sword", 1.f, 2.f, this, new RegionOfInterest(16, 0, 16, 32));
    }

	/**
	 * This method is used if the player took the sword
	 */
    public void collect() {
        isTaken = true;
    }

    @Override public boolean takeCellSpace() { return true; }
    @Override public boolean isCellInteractable() { return false; }
    @Override public boolean isViewInteractable() { return !isTaken; }

    @Override
    public void draw(Canvas canvas) {
        if (isTaken) {
            takenSprite.draw(canvas);
        } else {
            normalSprite.draw(canvas);
        }
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v) {
    	((ARPGInteractionVisitor)v).interactWith(this);
    }
}
