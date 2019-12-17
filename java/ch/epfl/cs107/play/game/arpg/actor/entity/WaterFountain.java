package ch.epfl.cs107.play.game.arpg.actor.entity;

import ch.epfl.cs107.play.game.actor.SoundAcoustics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Audio;
import ch.epfl.cs107.play.window.Canvas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WaterFountain extends AreaEntity {

    private static final int ANIMATION_FRAME_LENGTH = 3;

    private Animation animation;
    private SoundAcoustics sound;

    public WaterFountain(Area area, DiscreteCoordinates position) {
        super(area, Orientation.DOWN, position);

        sound = new SoundAcoustics(ResourcePath.getSounds("random/waterFountain"), 0.15f, true, false, true, false);
        sound.shouldBeStarted();

        Sprite[] sprites = new Sprite[ANIMATION_FRAME_LENGTH];
        for (int i = 0; i < ANIMATION_FRAME_LENGTH; i++) {
            sprites[i] = new RPGSprite("custom/fountain", 3.f, 3.f, this, new RegionOfInterest((i*48), 0, 48, 45), new Vector(-1f, -1f));
        }
        animation = new Animation(sprites.length, sprites, true);
    }

    @Override
    public void update(float deltaTime) {
        animation.update(deltaTime);
        super.update(deltaTime);
    }

    @Override
    public void draw(Canvas canvas) {
        animation.draw(canvas);
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        List<DiscreteCoordinates> ret = new ArrayList<>();
        for (int i = getCurrentMainCellCoordinates().x - 1; i <= getCurrentMainCellCoordinates().x + 1; i++) {
            for (int j = getCurrentMainCellCoordinates().y - 1; j <= getCurrentMainCellCoordinates().y + 1; j++) {
                ret.add(new DiscreteCoordinates(i, j));
            }
        }
        return Collections.unmodifiableList(ret);
    }

    @Override public boolean takeCellSpace() { return true; }
    @Override public boolean isCellInteractable() { return false; }
    @Override public boolean isViewInteractable() { return false; }
    @Override public void acceptInteraction(AreaInteractionVisitor v) { }

    @Override
    public void bip(Audio audio) {
        sound.bip(audio);
    }

}
