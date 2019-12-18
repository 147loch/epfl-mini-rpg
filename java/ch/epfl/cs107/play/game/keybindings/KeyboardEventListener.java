package ch.epfl.cs107.play.game.keybindings;

import java.util.List;

/**
 * This interface defines an event listener and the callback that needs to be called when
 * the linked keyboard event is activated. The advantage this provides is that we can use
 * this to access the class where the callback is defined as abstractly as possible. The
 * event register/manager will not be able to access the event's parent class anywhere.
 */
public interface KeyboardEventListener {

    /**
     * A callback which needs to accept an action to be dynamically adapted to it.
     * @param action The action that triggered the event
     */
    void onKeyEvent(KeyboardAction action);

    /**
     * This is a method to defined in the listener which actions it should listen to,
     * @return The list of Keyboard actions
     */
    List<KeyboardAction> getActions();

    /**
     * This method will triger if not empty when a key is released
     * @param previousAction The action which was released
     */
    default void onKeyReleasedEvent(KeyboardAction previousAction) {};

}
