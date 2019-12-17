package ch.epfl.cs107.play.game.arpg.actor.puzzle;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.arpg.actor.puzzle.Activatable;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Canvas;

public class CaveDoor extends Door implements Activatable {

    private Sprite doorClosed;
    private Sprite doorOpened;

    private boolean isOpened;

    public CaveDoor(String destination, DiscreteCoordinates otherSideCoordinates, Area area,
                      Orientation orientation, DiscreteCoordinates position, DiscreteCoordinates ...otherPositions) {
        super(destination, otherSideCoordinates, Logic.TRUE, area, orientation, position, otherPositions);

        this.isOpened = false;

        doorOpened = new RPGSprite("zelda/cave.open", 1.f, 1.f, this);
        doorClosed = new RPGSprite("zelda/cave.close", 1.f, 1.f, this);
    }

    public boolean changeSignal() {
        isOpened = !isOpened;
        return isOpened;
    }

    @Override
    public boolean takeCellSpace() {
        return !isOpened;
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
        if (isOpened)
            doorOpened.draw(canvas);
        else
            doorClosed.draw(canvas);
    }

    @Override
    public void activeEntity() {
        isOpened = true;
    }
}
