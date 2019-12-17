package ch.epfl.cs107.play.game.arpg.actor.battle.monster;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.game.arpg.actor.battle.DamageType;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Audio;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class MonsterEntity extends MovableAreaEntity implements Interactor {

    protected enum State {
        IDLE,
        FALLING_ASLEEP,
        ASLEEP,
        WAKING_UP,
        ATTACK,
        INVOKE,
        TELEPORTATION,
        IS_GOING_TO_TELEPORT,
        DEAD
    }

    private static final int ANIMATION_VANISH_FRAME_LENGTH = 7;
    private static final int INVICIBILITY_COUNTER = 15;

    private float currentHealth;
    private float maxHealth;
    private int invicibilityCounter;

    private Animation animationVanish;
    private State currentState;

    private List<DamageType> vulnerabilities;

    public MonsterEntity(Area area, Orientation orientation, DiscreteCoordinates coordinates, float maxHealth, DamageType[] vulnerabilities) {
        super(area, orientation, coordinates);
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.vulnerabilities = Arrays.asList(vulnerabilities);

        currentState = State.IDLE;
        invicibilityCounter = -1;

        RPGSprite[] vanishAnimationSprites = new RPGSprite[ANIMATION_VANISH_FRAME_LENGTH];
        for (int i = 0; i < ANIMATION_VANISH_FRAME_LENGTH; i++)
            vanishAnimationSprites[i] = new RPGSprite(
                    "zelda/vanish", 2.f, 2.f, this,
                    new RegionOfInterest((i*32), 0, 32, 32), new Vector(-0.5f, 0)
            );
        animationVanish = new Animation(1, vanishAnimationSprites, false);
    }

    // Final Methods
    public final void takeDamage(DamageType damageType, float damage) {
        if (vulnerabilities.contains(damageType) && invicibilityCounter < 0) {
            if (currentHealth - damage <= 0) {
                currentHealth = 0;
                this.currentState = State.DEAD;
            } else {
                currentHealth -= damage;
                invicibilityCounter = INVICIBILITY_COUNTER;
                handleDamageEvent(damage);
            }
        }
    }

    // Abstracted methods
    protected abstract void handleDamageEvent(float damageTook);
    protected abstract void handleDeathDropEvent();

    // Accessor / Mutator
    public final float getMaxHealth() { return maxHealth; }
    public final float getCurrentHealth() { return currentHealth; }

    protected final State getCurrentState() { return currentState; }
    protected void setCurrentState(State state) { currentState = state; }

    // Overrides
    @Override public boolean isCellInteractable() { return true; }
    @Override public boolean isViewInteractable() { return true; }
    @Override public boolean takeCellSpace() { return !State.DEAD.equals(this.currentState); }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v) {
        ((ARPGInteractionVisitor)v).interactWith(this);
    }

    @Override
    public final List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if (currentHealth <= 0 && !State.DEAD.equals(currentState)) {
            currentState = State.DEAD;
            currentHealth = 0;
        }

        if (invicibilityCounter >= 0) {
            invicibilityCounter--;
        }

        if (State.DEAD.equals(currentState))
            animationVanish.update(deltaTime);

        if (animationVanish.isCompleted()) {
            handleDeathDropEvent();
            getOwnerArea().unregisterActor(this);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if (State.DEAD.equals(currentState) && !animationVanish.isCompleted()) {
            animationVanish.draw(canvas);
        }
    }

    @Override
    public void bip(Audio audio) {
        if (invicibilityCounter == 0) {
            audio.playSound(audio.getSound(ResourcePath.getSounds("custom/sw.damage.monster")), false, 0.75f, false, false, false);
        }

        if (State.DEAD.equals(currentState) && animationVanish.isCompleted()) {
            audio.playSound(audio.getSound(ResourcePath.getSounds("custom/sw.monster.death")), false, 0.75f, false, false, false);
        }
    }
}
