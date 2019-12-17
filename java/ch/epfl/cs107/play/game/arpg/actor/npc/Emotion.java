package ch.epfl.cs107.play.game.arpg.actor.npc;

public enum Emotion {

	EMPTY(0, 0),
	THREEDOTS(16, 0),
	ONEDOT(0, 16),
	TWODOTS(16, 16),
	EXCLAMATION(0, 32),
	INTERROGATION(16, 32);
	
	private Emotion(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	private int x;
	private int y;
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
}
