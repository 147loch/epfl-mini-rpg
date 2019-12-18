package ch.epfl.cs107.play.game.keybindings;

public class Event {

    private KeyboardAction action;
    private KeyboardEventListener listener;
    private boolean canKeepPressed = false;

    /**
     * An event definition, where the key should only be pressed, not maintained.
     * @param action The linked KeyboardAction on which the event should react
     * @param listener The linked listener with the callback which should be called if the key is pressed
     */
    Event(KeyboardAction action, KeyboardEventListener listener) {
        this.action = action;
        this.listener = listener;
    }

    /**
     * An event definition
     * @param action The linked KeyboardAction on which the event should react
     * @param listener The linked listener with the callback which should be called if the key is pressed
     * @param canKeepPressed Whether the event register should look if the key has only been pressed once
     *                      or it can be pressed down continuously (eg. movements).
     */
    Event(KeyboardAction action, KeyboardEventListener listener, boolean canKeepPressed) {
        this(action, listener);
        this.canKeepPressed = canKeepPressed;
    }
    
    /**
     * Method called by the event register if the linked key was pressed
     * @param action The linked action which is then passed to the listener (dynamic)
     * @see StaticKeyboardEventListener
     * @see KeyboardEventListener
     */
    protected void onKeyEvent(KeyboardAction action) {
        listener.onKeyEvent(action);
    }

    /**
     * Method called by the event register if the linked key was released
     * @param action The linked action which is then passed to the listener (dynamic)
     * @see StaticKeyboardEventListener
     * @see KeyboardEventListener
     */
    protected void onKeyReleasedEvent(KeyboardAction action) {
        listener.onKeyReleasedEvent(action);
    }

    /**
     * Getter to get the linked property
     * @return Linked action
     */
    public KeyboardAction getAction() {
        return action;
    }

    /**
     * Getter to get whether the key should be checked if pressed once or continuously held down
     * @return True if the key can be held down
     */
    public boolean canKeepPressed() {
        return canKeepPressed;
    }

}
