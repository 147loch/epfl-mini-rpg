package ch.epfl.cs107.play.game.arpg.actor.monster;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
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
        DEAD
    }

    private static final int ANIMATION_VANISH_FRAME_LENGTH = 7;

    private float currentHealth;
    private float maxHealth;

    private Animation animationVanish;
    private State currentState;

    private List<DamageType> vulnerabilities;

    public MonsterEntity(Area area, Orientation orientation, DiscreteCoordinates coordinates, float maxHealth, DamageType[] vulnerabilities) {
        super(area, orientation, coordinates);
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.vulnerabilities = Arrays.asList(vulnerabilities);

        currentState = State.IDLE;

        RPGSprite[] vanishAnimationSprites = new RPGSprite[ANIMATION_VANISH_FRAME_LENGTH];
        for (int i = 0; i < ANIMATION_VANISH_FRAME_LENGTH; i++)
            vanishAnimationSprites[i] = new RPGSprite(
                    "zelda/vanish", 2.f, 2.f, this,
                    new RegionOfInterest((i*32), 0, 32, 32), new Vector(-0.5f, 0)
            );
        animationVanish = new Animation(1, vanishAnimationSprites, false);
    }

    // Methods
    public void takeDamage(DamageType damageType, float damage) {
        if (vulnerabilities.contains(damageType)) {
            if (currentHealth - damage <= 0) {
                currentHealth = 0;
                this.currentState = State.DEAD;
            } else {
                currentHealth -= damage;
                handleDamageEvent(damage);
            }
        }
    }

    // Abstracted methods
    protected abstract void handleDamageEvent(float damageTook); // TODO not necessarily useful, need to check
    protected abstract void handleDeathDropEvent();

    // Accessor / Mutator
    public float getMaxHealth() { return maxHealth; }
    public float getCurrentHealth() { return currentHealth; }

    protected State getCurrentState() { return currentState; }
    protected void setCurrentState(State state) { currentState = state; }

    // Overrides
    @Override public boolean isCellInteractable() { return true; }
    @Override public boolean isViewInteractable() { return true; }
    @Override public boolean takeCellSpace() { return !State.DEAD.equals(this.currentState); }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if (currentHealth <= 0 && !State.DEAD.equals(currentState)) {
            currentState = State.DEAD;
            currentHealth = 0;
        }

        if (State.DEAD.equals(currentState))
            animationVanish.update(deltaTime);

        if (animationVanish.isCompleted()) {
            getOwnerArea().unregisterActor(this);
            handleDeathDropEvent();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if (State.DEAD.equals(currentState) && !animationVanish.isCompleted()) {
            animationVanish.draw(canvas);
        }
    }
}
