package ch.epfl.cs107.play.game.tutos.area;

import ch.epfl.cs107.play.game.areagame.AreaBehavior;
import ch.epfl.cs107.play.window.Window;

public class Tuto2Behavior extends AreaBehavior {

	public enum Tuto2CellType {
		NULL(0, false),
		WALL(-16777216, false),
		IMPASSABLE(-8750470, false),
		INTERACT(-256, true),
		DOOR(-195580, true),
		WALKABLE(-1, true);
		
		final int type;
		final boolean isWalkable;
		
		Tuto2CellType(int type, boolean isWalkable) {
			this.type = type;
			this.isWalkable = isWalkable;
		}
		
		public static Tuto2CellType toType(int type) {
			for (Tuto2CellType t : Tuto2CellType.values()) {
				if (type == t.type) {
					return t;
				}
			}
			return NULL;
		}
	}
}
