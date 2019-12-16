package ch.epfl.cs107.play.game.arpg.actor.battle.monster;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.arpg.actor.ARPGPlayer;
import ch.epfl.cs107.play.game.arpg.actor.collectable.Coin;
import ch.epfl.cs107.play.game.arpg.actor.battle.DamageType;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RandomGenerator;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class LogMonster extends MonsterEntity {

    private static final int ANIMATION_DURATION = 4;
    private static final int MAXIMUM_INACTIVITY_COUNTER = 24;
    private static final int IDLE_MOVEMENT_FRAMES = 12;
    private static final int DASH_MOVEMENT_FRAMES = 4;

    private static final int MIN_SLEEPING_DURATION = 18;
    private static final int MAX_SLEEPING_DURATION = 36;

    private static final float PLAYER_ATTACK_DAMAGE = 2.f;

    private static final DamageType[] VULNERABILITIES = {DamageType.PHYSICAL, DamageType.FIRE};

    private Animation[] walkingAnimations;
    private Animation currentAnimation;
    private Animation sleepingAnimation;
    private Animation wakingUpAnimation;
    private Animation fallingAsleepAnimation;

    private ARPGLogMonsterHandler handler;

    private int inactivityCounter;

    public LogMonster(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position, 2.f, VULNERABILITIES);

        Sprite[][] walkingSprites = RPGSprite.extractSprites(
            "zelda/logMonster", 4, 2, 2, this , 32, 32,
            new Vector(-0.5f, 0), new Orientation[] {Orientation.DOWN , Orientation.UP , Orientation.RIGHT, Orientation.LEFT}
        );
        walkingAnimations = RPGSprite.createAnimations(ANIMATION_DURATION, walkingSprites);
        currentAnimation = walkingAnimations[orientation.ordinal()];

        Sprite[] sleepingSprites = new Sprite[4];
        for (int i = 0; i < sleepingSprites.length; i++) {
            sleepingSprites[i] = new RPGSprite("zelda/logMonster.sleeping", 2, 2, this,
                    new RegionOfInterest(0, 32 * i, 32, 32), new Vector(-0.5f, 0));
        }
        sleepingAnimation = new Animation(sleepingSprites.length, sleepingSprites, true);

        Sprite[] wakingUpSprites = new Sprite[3];
        for (int i = 0; i < wakingUpSprites.length; i++) {
            wakingUpSprites[i] = new RPGSprite("zelda/logMonster.wakingUp", 2, 2, this,
                    new RegionOfInterest(0, 32 * i, 32, 32), new Vector(-0.5f, 0));
        }
        wakingUpAnimation = new Animation(wakingUpSprites.length, wakingUpSprites, false);
        List<Sprite> reversedWakingUpList = Arrays.asList(wakingUpSprites);
        Collections.reverse(reversedWakingUpList);
        Sprite[] fallingAsleepSprites = reversedWakingUpList.toArray(wakingUpSprites);
        fallingAsleepAnimation = new Animation(fallingAsleepSprites.length, fallingAsleepSprites, false);

        handler = new ARPGLogMonsterHandler();

        inactivityCounter = 0;
    }

    // MonsterEntity events
    @Override protected void handleDamageEvent(float damageTook) {}
    @Override protected void handleDeathDropEvent() {
        getOwnerArea().registerActor(new Coin(getOwnerArea(), Orientation.UP, getCurrentMainCellCoordinates()));
    }

    // Methods
    private void handleIdleMovement() {
        if (!this.isDisplacementOccurs()) {
            if (RandomGenerator.getInstance().nextDouble() > 0.6) {
                resetMotion();
                orientate(Orientation.fromInt(RandomGenerator.getInstance().nextInt(4)));
                currentAnimation = walkingAnimations[getOrientation().ordinal()];
            } else {
                move(IDLE_MOVEMENT_FRAMES);
            }
        }
        
        inactivityCounter = RandomGenerator.getInstance().nextInt(MAXIMUM_INACTIVITY_COUNTER);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        switch (getCurrentState()) {
            case ATTACK:
            case IDLE:
                if (this.isDisplacementOccurs())
                    currentAnimation.update(deltaTime);
                else
                    currentAnimation.reset();
                break;
            case FALLING_ASLEEP:
                if (currentAnimation.equals(fallingAsleepAnimation) && currentAnimation.isCompleted()) {
                    fallingAsleepAnimation.reset();
                    currentAnimation = sleepingAnimation;
                    setCurrentState(State.ASLEEP);
                }
            default:
                currentAnimation.update(deltaTime);
        }

        if (inactivityCounter > 0) {
            inactivityCounter--;
        }

        if (inactivityCounter == 0) {
            switch (getCurrentState()) {
                case IDLE:
                    currentAnimation = walkingAnimations[getOrientation().ordinal()];
                    handleIdleMovement();
                    break;
                case ATTACK:
                    if (!this.isDisplacementOccurs()) {
                        if (getOwnerArea().canEnterAreaCells(this, getFieldOfViewCells())) {
                            currentAnimation = walkingAnimations[getOrientation().ordinal()];
                            move(DASH_MOVEMENT_FRAMES);
                        } else {
                            setCurrentState(State.FALLING_ASLEEP);
                        }
                    }
                    break;
                case FALLING_ASLEEP:
                    inactivityCounter = RandomGenerator.getInstance().nextInt(MAX_SLEEPING_DURATION - MIN_SLEEPING_DURATION) + MIN_SLEEPING_DURATION;
                    currentAnimation = fallingAsleepAnimation;
                    break;
                case ASLEEP:
                    sleepingAnimation.reset();
                    currentAnimation = wakingUpAnimation;
                    setCurrentState(State.WAKING_UP);
                    break;
                case WAKING_UP:
                    if (currentAnimation.isCompleted()) {
                        wakingUpAnimation.reset();
                        setCurrentState(State.IDLE);
                        inactivityCounter = RandomGenerator.getInstance().nextInt(MAXIMUM_INACTIVITY_COUNTER);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void interactWith(Interactable other) {
        other.acceptInteraction(handler);
    }

    @Override
    public void draw(Canvas canvas) {
        if (!State.DEAD.equals(getCurrentState()) && !currentAnimation.isCompleted()) {
            currentAnimation.draw(canvas);
        }
        super.draw(canvas);
    }

    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        switch (getCurrentState()) {
            case ATTACK:
                return Collections.singletonList(getCurrentMainCellCoordinates().jump(getOrientation().toVector()));
            case IDLE:
                List<DiscreteCoordinates> ret = new ArrayList<>();
                for (int i = 1; i <= 8; i++)
                    ret.add(getCurrentMainCellCoordinates().jump(getOrientation().toVector().mul(i)));
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
        return State.IDLE.equals(getCurrentState()) || State.ATTACK.equals(getCurrentState());
    }

    private class ARPGLogMonsterHandler implements ARPGInteractionVisitor {
        @Override
        public void interactWith(ARPGPlayer player) {
            switch (getCurrentState()) {
                case IDLE:
                    setCurrentState(State.ATTACK);
                    break;
                case ATTACK:
                    player.takeDamage(PLAYER_ATTACK_DAMAGE);
                    break;
                default:
                    break;
            }
        }
    }
}
