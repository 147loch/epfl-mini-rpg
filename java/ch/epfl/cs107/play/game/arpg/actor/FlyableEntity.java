package ch.epfl.cs107.play.game.arpg.actor;

public interface FlyableEntity {

	/**
	 * This method is used to know if an entity can fly
	 * @return true, if the entity can fly
	 */
	default public boolean canFly() { return true; }
	
}
