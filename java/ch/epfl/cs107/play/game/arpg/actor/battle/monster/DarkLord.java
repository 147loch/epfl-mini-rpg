package ch.epfl.cs107.play.game.arpg.actor.battle.monster;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.arpg.actor.ARPGPlayer;
import ch.epfl.cs107.play.game.arpg.actor.battle.DamageType;
import ch.epfl.cs107.play.game.arpg.handler.ARPGInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class DarkLord extends MonsterEntity {
	
	private ARPGDarkLordHandler handler;

	public DarkLord(Area area, Orientation orientation, DiscreteCoordinates coordinates, float maxHealth,
			DamageType[] vulnerabilities) {
		super(area, orientation, coordinates, maxHealth, vulnerabilities);
		
		handler = new ARPGDarkLordHandler();
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
		return false;
	}

	@Override
	public void interactWith(Interactable other) {
		other.acceptInteraction(handler);
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		((ARPGInteractionVisitor)v).interactWith(this);
	}

	@Override
	protected void handleDamageEvent(float damageTook) {}
	@Override
	protected void handleDeathDropEvent() {}

	private class ARPGDarkLordHandler implements ARPGInteractionVisitor {
		
		@Override
		public void interactWith(ARPGPlayer player) {
			
		}
	}
}
