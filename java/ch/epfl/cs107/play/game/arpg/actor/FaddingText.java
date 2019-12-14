package ch.epfl.cs107.play.game.arpg.actor;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Entity;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class FaddingText extends Entity {

	private static final int INDEX_FADING = 10;
	
	private boolean isOver;
	private TextGraphics text;
	private float alpha;
	private int indexBeforeFading;
	
	public FaddingText(Vector position) {
		super(position);
		text = new TextGraphics("❤", 1, Color.RED);
		isOver = true;
		text.setParent(this);
		alpha = text.getAlpha();
		indexBeforeFading = 0;
		
		text.setAnchor(new Vector(-1.f, 0.5f));
	}
	
	protected void init(String text, Vector position) {
		this.text.setText(text);
		isOver = false;
		indexBeforeFading = 0;
		alpha = 1;
		setCurrentPosition(position);
	}
	
	@Override
	public void update(float deltaTime) {
		if (indexBeforeFading >= INDEX_FADING) {
			if (alpha <= 0) {
				isOver = true;
			} else {
				alpha -= 0.05f;
				text.setAlpha(alpha);
			}
		} else {
			indexBeforeFading++;
		}
		
		super.update(deltaTime);
	}

	@Override
	public void draw(Canvas canvas) {
		if (!isOver) {
			text.draw(canvas);
		}
	}
}
