package ch.epfl.cs107.play.game.arpg.actor;

import ch.epfl.cs107.play.game.actor.Entity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.SoundAcoustics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Audio;
import ch.epfl.cs107.play.window.Canvas;


public class GameOver extends Entity {

    private final float DX;
    private final float DY;

    private SoundAcoustics sound;

    /// Sprite of the actor
    private final ImageGraphics sprite;

    /**
     * Default Foreground Constructor
     * by default the Background image is using the area title as file name
     * @param area (Area): ownerArea, not null
     */
    public GameOver(Area area) {
        super(Vector.ZERO);
        DX = area.getCameraScaleFactor()/2;
        DY = area.getCameraScaleFactor()/2;
        final float side = area.getCameraScaleFactor();

        sound = new SoundAcoustics(ResourcePath.getSounds("custom/sw.death"), 0.65f, false, false, false, true);
        sound.shouldBeStarted();

        sprite = new ImageGraphics(ResourcePath.getForegrounds("custom/gameOver"), side, side, null, Vector.ZERO, 1.0f, 10000);
    }

    /// Foreground implements Graphics

    @Override
    public void draw(Canvas canvas) {

        final Transform transform = Transform.I.translated(canvas.getPosition().sub(DX, DY));
        sprite.setRelativeTransform(transform);
        sprite.draw(canvas);
    }

    @Override
    public void bip(Audio audio) {
        sound.bip(audio);
    }
}
