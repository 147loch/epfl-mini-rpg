package ch.epfl.cs107.play.game.arpg.actor.monster;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class LogMonster extends MonsterEntity {

    private static final int ANIMATION_DURATION = 4;

    private Animation[] walkingAnimations;

    private ARPGLogMonsterHandler handler;

    public LogMonster(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position, 2.f);

        Sprite[][] walkingSprites = RPGSprite.extractSprites("zelda/logMonster", 4, 2, 2, this , 32, 32, new Orientation[]
                {Orientation.UP , Orientation.DOWN , Orientation.LEFT, Orientation.RIGHT});
        walkingAnimations = RPGSprite.createAnimations(ANIMATION_DURATION, walkingSprites);
        for (Animation walkingAnimation : walkingAnimations) {
            walkingAnimation.setAnchor(new Vector(this.getTransform().getX().getY() - 0.5f, this.getTransform().getX().getY()));
        }
        setAnimation(walkingAnimations[0]);

        handler = new ARPGLogMonsterHandler();
    }

    @Override
    public void update(float deltaTime) {
        setAnimation(walkingAnimations[getOrientation().opposite().ordinal()]);

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

    @Override
    public boolean canFly() {
        return false;
    }

    private class ARPGLogMonsterHandler implements ARPGInteractionVisitor {

    }
}
