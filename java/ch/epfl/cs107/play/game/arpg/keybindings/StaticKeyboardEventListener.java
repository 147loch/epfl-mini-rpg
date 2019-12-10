package ch.epfl.cs107.play.game.arpg.keybindings;

import java.util.Collections;
import java.util.List;

/**
 * A static keyboard event listener is just an extension of the dynamic version, where the action is ignored.
 * @see ch.epfl.cs107.play.game.arpg.keybindings.KeyboardEventListener
 */
public interface StaticKeyboardEventListener extends KeyboardEventListener {

    @Override
    default void onKeyEvent(KeyboardAction action) { onKeyEvent(); }

    @Override
    default List<KeyboardAction> getActions() { return Collections.emptyList(); }

    void onKeyEvent();

}
