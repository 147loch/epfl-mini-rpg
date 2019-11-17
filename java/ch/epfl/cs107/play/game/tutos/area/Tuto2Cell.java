package ch.epfl.cs107.play.game.tutos.area;

import ch.epfl.cs107.play.game.areagame.Cell;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.tutos.area.Tuto2Behavior.Tuto2CellType;

public class Tuto2Cell extends Cell {
	private Tuto2Cell(int x, int y, Tuto2CellType type) {
		super(x, y);
	}

	@Override
	public boolean isCellInteractable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isViewInteractable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean canLeave(Interactable entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean canEnter(Interactable entity) {
		// TODO Auto-generated method stub
		return false;
	}
}
