package ch.epfl.cs107.play.game.arpg.actor.battle;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.arpg.actor.monster.DamageType;
import ch.epfl.cs107.play.game.arpg.actor.monster.FlyableEntity;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public abstract class Projectile extends MovableAreaEntity implements FlyableEntity, Interactor {
	
	private DamageType damageType;
	private int frameSpeed;
	private float maxLifeTime;
	
	private float currentLifeTime;
	
	public Projectile(Area area, Orientation orientation, DiscreteCoordinates position, DamageType dt, int frameSpeed, float maxLifeTime) {
		super(area, orientation, position);
		damageType = dt;
		currentLifeTime = 0;
		this.maxLifeTime = maxLifeTime;
		this.frameSpeed = frameSpeed;
	}
	
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
		return false;
	}

	@Override
	public boolean isViewInteractable() {
		return true;
	}
	
	@Override
	public boolean wantsCellInteraction() {
		return false;
	}

	@Override
	public boolean wantsViewInteraction() {
		return true;
	}
}
