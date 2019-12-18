package ch.epfl.cs107.play.game.arpg.actor.battle.weapon;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.arpg.actor.FlyableEntity;
import ch.epfl.cs107.play.game.arpg.actor.battle.DamageType;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public abstract class Projectile extends MovableAreaEntity implements FlyableEntity, Interactor {
	
	private DamageType damageType;
	private int frameSpeed;
	private float maxLifeTime;
	
	private float currentLifeTime;
	
	/**
	 * Constructor for the abstract class Projectile
	 * @param area the area
	 * @param orientation the orientation
	 * @param position the position
	 * @param dt the damage type
	 * @param frameSpeed the movement speed
	 * @param maxLifeTime the lifetime, addition of deltaTime
	 */
	public Projectile(Area area, Orientation orientation, DiscreteCoordinates position, DamageType dt, int frameSpeed, float maxLifeTime) {
		super(area, orientation, position);
		damageType = dt;
		currentLifeTime = 0;
		this.maxLifeTime = maxLifeTime;
		this.frameSpeed = frameSpeed;
	}
	
	/**
     * Return the DamageType of the projectile
     * @return the type of damage of the projectile
     * @see DamageType
     */
	protected DamageType getDamageType() {
		return damageType;
	}
	
	@Override
	public void update(float deltaTime) {
		move(frameSpeed);
		currentLifeTime += deltaTime;
		
		if (!isDisplacementOccurs() || currentLifeTime >= maxLifeTime)
			getOwnerArea().unregisterActor(this);
		
		super.update(deltaTime);
	}
	
	@Override
	public List<DiscreteCoordinates> getCurrentCells() {
		return Collections.singletonList(getCurrentMainCellCoordinates());
	}
	
	@Override
	public List<DiscreteCoordinates> getFieldOfViewCells() {
		return Collections.singletonList(getCurrentMainCellCoordinates().jump(getOrientation().toVector()));
	}

	@Override
	public boolean takeCellSpace() {
		return false;
	}

	@Override
	public boolean isCellInteractable() {
		return true;
	}

	@Override
	public boolean isViewInteractable() {
		return false;
	}
	
	@Override
	public boolean wantsCellInteraction() {
		return true;
	}

	@Override
	public boolean wantsViewInteraction() {
		return false;
	}
}
