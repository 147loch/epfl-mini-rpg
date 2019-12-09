package ch.epfl.cs107.play.game.arpg.keybindings;

/**
 * A static keyboard event listener is just an extension of the dynamic version, where the action is ignored.
 * @see ch.epfl.cs107.play.game.arpg.keybindings.KeyboardEventListener
 */
public interface StaticKeyboardEventListener extends KeyboardEventListener {

    default void onKeyEvent(KeyboardAction action) { onKeyEvent(); }

    void onKeyEvent();

}
