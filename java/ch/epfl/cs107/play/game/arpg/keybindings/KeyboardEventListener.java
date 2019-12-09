package ch.epfl.cs107.play.game.arpg.keybindings;

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

}
