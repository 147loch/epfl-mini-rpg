package ch.epfl.cs107.play.game.arpg.actor.puzzle;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Audio;
import ch.epfl.cs107.play.window.Canvas;

public class PressurePlate extends AreaEntity {

    private Sprite onSprite;
    private Sprite offSprite;
    private Actionable entityActionable;
    private boolean isAlreadyActive;
    private boolean soundActive;

    public PressurePlate(Area area, Orientation orientation, DiscreteCoordinates position, Actionable entityActionable) {
        super(area, orientation, position);

        this.entityActionable = entityActionable;
        isAlreadyActive = false;
        soundActive = false;

        offSprite = new RPGSprite("custom/pressurePlate", 1.f, 1.f, this, new RegionOfInterest(0, 0, 16, 16), new Vector(0, 0.2f));
        onSprite = new RPGSprite("custom/pressurePlate", 1.f, 1.f, this, new RegionOfInterest(16, 0, 16, 16), new Vector(0, 0.2f));
    }

    public void active() {
        if (!isAlreadyActive) {
            entityActionable.activeEntity();
            isAlreadyActive = true;
            soundActive = true;
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

    @Override
    public void bip(Audio audio) {
        if (soundActive) {
            soundActive = false;
            audio.playSound(audio.getSound(ResourcePath.getSounds("custom/sw.pressurePlate")), false, 0.75f, false, false, false);
        }
    }

}
