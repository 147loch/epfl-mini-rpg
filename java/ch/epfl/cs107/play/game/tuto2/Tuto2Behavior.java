package ch.epfl.cs107.play.game.tuto2;

import ch.epfl.cs107.play.game.areagame.AreaBehavior;
import ch.epfl.cs107.play.game.areagame.Cell;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.window.Window;

public class Tuto2Behavior extends AreaBehavior {

	public enum Tuto2CellType {
		NULL(0, false),
		WALL(-16777216, false),
		IMPASSABLE(-8750470, false),
		INTERACT(-256, true),
		DOOR(-195580, true),
		WALKABLE(-1, true);
		
		private final int type;
		private final boolean isWalkable;
		
		Tuto2CellType(int type, boolean isWalkable) {
			this.type = type;
			this.isWalkable = isWalkable;
		}
		
		private static Tuto2CellType toType(int type) {
			for (Tuto2CellType p : Tuto2CellType.values()) {
				if (p.type == type) {
					return p;
				}
			}
			
			return NULL;
		}
	}
	
	public Tuto2Behavior(Window window, String name) {
		super(window, name);
		
		for (int x = 0; x < this.getWidth(); x++) {
			for (int y = 0; y < this.getHeight(); y++) {
				Tuto2CellType cellType = Tuto2CellType.toType(getRGB(getHeight()-1-y, x));
				this.setCell(x, y, new Tuto2Cell(x, y, cellType));
			}
		}
	}
	
	public class Tuto2Cell extends Cell {

		private Tuto2CellType type;
		
		private Tuto2Cell(int x, int y, Tuto2CellType type) {
			super(x, y);
			this.type = type;
		}

		public Tuto2CellType getType() {
			return type;
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
}
