package ch.epfl.cs107.play.game.arpg.actor;

public interface FlyableEntity {

	default public boolean canFly() { return true; }
	
}
