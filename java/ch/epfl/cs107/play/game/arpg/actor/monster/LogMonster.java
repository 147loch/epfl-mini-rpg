package ch.epfl.cs107.play.game.arpg.actor.monster;

import java.util.ArrayList;
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
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class LogMonster extends MonsterEntity {

    private static final int ANIMATION_DURATION = 4;
    private static final float PLAYER_ATTACK_DAMAGE = 2.f;
    private static final DamageType[] VULNERABILITIES = {DamageType.PHYSICAL, DamageType.FIRE};

    private Animation[] walkingAnimations;
    private Animation sleepingAnimation;
    private Animation wakingUpAnimation;

    private ARPGLogMonsterHandler handler;

    public LogMonster(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position, 2.f, VULNERABILITIES);

        Sprite[][] walkingSprites = RPGSprite.extractSprites(
            "zelda/logMonster", 4, 2, 2, this , 32, 32,
            new Vector(-0.5f, 0), new Orientation[] {Orientation.DOWN , Orientation.UP , Orientation.RIGHT, Orientation.LEFT}
        );
        walkingAnimations = RPGSprite.createAnimations(ANIMATION_DURATION, walkingSprites);
        setAnimation(walkingAnimations[0]);

        Sprite[] sleepingSprites = new Sprite[4];
        for (int i = 0; i > sleepingSprites.length; i++) {
            sleepingSprites[i] = new RPGSprite("zelda/logMonster.sleeping", 1, 1, this,
                    new RegionOfInterest(0, 32 * i, 32, 32));
        }
        sleepingAnimation = new Animation(sleepingSprites.length, sleepingSprites, true);

        Sprite[] wakingUpSprites = new Sprite[3];
        for (int i = 0; i < wakingUpSprites.length; i++) {
            wakingUpSprites[i] = new RPGSprite("zelda/logMonster.wakingUp", 1, 1, this,
                    new RegionOfInterest(0, 32 * i, 32, 32));
        }
        wakingUpAnimation = new Animation(wakingUpSprites.length, wakingUpSprites, true);

        handler = new ARPGLogMonsterHandler();
    }

    @Override
    public void update(float deltaTime) {
        switch(getState()) {
            case WAKING_UP:
                if (!Orientation.DOWN.equals(getOrientation()))
                    orientate(Orientation.DOWN);
                setAnimation(wakingUpAnimation);
                break;
            case SLEEP:
                if (!Orientation.DOWN.equals(getOrientation()))
                    orientate(Orientation.DOWN);
                setAnimation(sleepingAnimation);
                break;
            case IDLE:
                setAnimation(walkingAnimations[getOrientation().ordinal()]);
                break;
            default:
                break;
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
        switch (getState()) {
            case ATTACK:
                return Collections.singletonList(getCurrentMainCellCoordinates().jump(getOrientation().toVector()));
            case IDLE:
                List<DiscreteCoordinates> ret = new ArrayList<>();
                for (int i = 1; i <= 8; i++) {
                    ret.add(getCurrentMainCellCoordinates().jump(getOrientation().toVector().mul(i)));
                }
                return Collections.unmodifiableList(ret);
            default:
                return Collections.emptyList();
        }
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
