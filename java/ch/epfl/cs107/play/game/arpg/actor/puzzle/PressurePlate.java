package ch.epfl.cs107.play.game.arpg.actor.puzzle;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.game.rpg.actor.Sign;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

public class PressurePlate extends AreaEntity {

    private Sprite onSprite;
    private Sprite offSprite;
    private Activatable entityActivatable;
    private boolean isAlreadyActive;

    public PressurePlate(Area area, Orientation orientation, DiscreteCoordinates position, Activatable entityActivatable) {
        super(area, orientation, position);

        this.entityActivatable = entityActivatable;
        isAlreadyActive = false;

        offSprite = new RPGSprite("custom/pressurePlate", 1.f, 1.f, this, new RegionOfInterest(0, 0, 16, 16), new Vector(0, 0.2f));
        onSprite = new RPGSprite("custom/pressurePlate", 1.f, 1.f, this, new RegionOfInterest(16, 0, 16, 16), new Vector(0, 0.2f));
    }

    public void active() {
        if (!isAlreadyActive) {
            entityActivatable.activeEntity();
            isAlreadyActive = true;
        }
    }

    @Override public boolean takeCellSpace() { return false; }
    @Override public boolean isCellInteractable() { return true; }
    @Override public boolean isViewInteractable() { return false; }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v) {
        ((ARPGInteractionVisitor)v).interactWith(this);
    }

    @Override
    public void draw(Canvas canvas) {
        if (isAlreadyActive)
            onSprite.draw(canvas);
        else
            offSprite.draw(canvas);
    }

}
