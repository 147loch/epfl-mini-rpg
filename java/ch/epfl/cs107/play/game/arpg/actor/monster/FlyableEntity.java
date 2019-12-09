package ch.epfl.cs107.play.game.arpg.actor.monster;

public interface FlyableEntity {

	default public boolean canFly() { return true; }
	
}
