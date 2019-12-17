package ch.epfl.cs107.play.game.arpg.actor.entity;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.arpg.actor.ARPGPlayer;
import ch.epfl.cs107.play.game.arpg.actor.battle.DamageType;
import ch.epfl.cs107.play.game.arpg.actor.battle.monster.MonsterEntity;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RandomGenerator;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

public class FireSpell extends AreaEntity implements Interactor {

    private static final int MIN_LIFE_TIME = 120;
    private static final int MAX_LIFE_TIME = 240;
    private static final int PROPAGATION_TIME_FIRE = 20;
    private static final int BASE_FORCE = 5;
    private static final int ANIMATION_SPEED = 2;

    private static final float PLAYER_INTERACTION_DAMAGE = 1.f;
    private static final float MONSTER_INTERACTION_DAMAGE = 0.5f;

    private FireSpellHandler handler;
    private Animation animation;
    private Animation animationVanish;

    private int force;
    private int extinguishCounter;
    private int propagationCounter;

    public FireSpell(Area area, Orientation orientation, DiscreteCoordinates coordinates, int force) {
        super(area, orientation, coordinates);

        this.force = force;
        this.extinguishCounter = RandomGenerator.getInstance().nextInt(MAX_LIFE_TIME - MIN_LIFE_TIME) + MIN_LIFE_TIME;
        this.propagationCounter = PROPAGATION_TIME_FIRE;

        Sprite[] animationSprites = new Sprite[7];
        for (int i = 0; i < animationSprites.length; i++) {
            animationSprites[i] = new RPGSprite("zelda/fire", 1, 1, this,
                    new RegionOfInterest(i*16, 0, 16, 16));
        }
        animation = new Animation(ANIMATION_SPEED, animationSprites);

        Sprite[] vanishAnimationSprites = new Sprite[7];
        for (int i = 0; i < vanishAnimationSprites.length; i++)
            vanishAnimationSprites[i] = new RPGSprite(
                    "zelda/vanishTeleportation", 1.f, 1.f, this,
                    new RegionOfInterest((i*32), 0, 32, 32), new Vector(-0.5f, 0)
            );
        animationVanish = new Animation(ANIMATION_SPEED/2, vanishAnimationSprites, false);

        handler = new FireSpellHandler();
    }

    public FireSpell(Area area, Orientation orientation, DiscreteCoordinates coordinates) {
        this(area, orientation, coordinates, BASE_FORCE);
    }

    private void handlePropagation() {
        if (force > 0) {
            getOwnerArea().registerActor(new FireSpell(getOwnerArea(), getOrientation(), getCurrentMainCellCoordinates().jump(getOrientation().toVector()), this.force - 1));
        }
    }

    @Override
    public void update(float deltaTime) {
        if (extinguishCounter > 0) {
            extinguishCounter--;
            animation.update(deltaTime);
        } else {
            animationVanish.update(deltaTime);
        }

        if (propagationCounter > 0) {
            propagationCounter--;
        } else if (propagationCounter == 0) {
            handlePropagation();
            propagationCounter = -1;
        }

        if (animationVanish.isCompleted()) {
            getOwnerArea().unregisterActor(this);
        }

        super.update(deltaTime);
    }

    @Override
    public void draw(Canvas canvas) {
        if (extinguishCounter > 0) {
            animation.draw(canvas);
        } else {
            if (!animationVanish.isCompleted()) {
                animationVanish.draw(canvas);
            }
        }
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override public boolean takeCellSpace() { return false; }
    @Override public boolean isCellInteractable() { return false; }
    @Override public boolean isViewInteractable() { return false; }
    @Override public boolean wantsCellInteraction() { return true; }
    @Override public boolean wantsViewInteraction() { return propagationCounter == 0; }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v) {
        ((ARPGInteractionVisitor)v).interactWith(this);
    }

    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates().jump(getOrientation().toVector()));
    }

    @Override
    public void interactWith(Interactable other) {
        other.acceptInteraction(handler);
    }

    private class FireSpellHandler implements ARPGInteractionVisitor {

        @Override
        public void interactWith(ARPGPlayer player) {
            player.takeDamage(PLAYER_INTERACTION_DAMAGE);
        }

        @Override
        public void interactWith(MonsterEntity monster) {
            monster.takeDamage(DamageType.FIRE, MONSTER_INTERACTION_DAMAGE);
        }

        @Override
        public void interactWith(Grass grass) {
            grass.cut(false);
        }

        @Override
        public void interactWith(Bomb bomb) {
            bomb.explode();
        }
    }
}
