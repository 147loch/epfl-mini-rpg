package ch.epfl.cs107.play.game.arpg.actor.collectable;

public enum Rarity {

	COMMON(0.5),
	UNCOMMON(0.25),
	RARE(0.1);
	
	private Rarity(double rarity) {
		this.rarity = rarity;
	}
	
	private final double rarity;
	
	public double getRarity() {
		return rarity;
	}
}
