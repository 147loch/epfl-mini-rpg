package ch.epfl.cs107.play.game.arpg.keybindings;

import ch.epfl.cs107.play.window.Keyboard;

import java.util.ArrayList;
import java.util.List;

public class KeyboardEventRegister {

    private List<Event> eventList = new ArrayList<>();
    private Keyboard keyboard;

    /**
     * Because the keyboard is linked to the SwingWindows itself, it does not need to be updated
     * with every area change. (As the keyboard can only be accessed through the area)
     * @param keyboard The keyboard associated to the SwingWindow
     */
    public KeyboardEventRegister(Keyboard keyboard) {
        this.keyboard = keyboard;
    }

    /**
     * This update method should be called upon each update of the class holding the register.
     * It verifies whether the key is down or pressed accordingly to the event settings and
     * executes the listener callbacks if they are.
     */
    public void update() {
        for (Event e: eventList) {
            if (e.canKeepPressed()) {
                if (e.getAction().getAssignedButton(keyboard).isDown())
                    e.onKeyEvent(e.getAction());
            } else {
                if (e.getAction().getAssignedButton(keyboard).isPressed())
                    e.onKeyEvent(e.getAction());
            }
        }
    }

    /**
     * Single event registration.
     * The action must be unique, so it will not be added if it has already been registered.
     * @param event The event
     * @return True if successfully added to the List
     */
    public boolean registerKeyboardEvent(Event event) {
        for (Event e: eventList)
            if (e.getAction().equals(event.getAction()))
                return false;
        return eventList.add(event);
    }

    /**
     * Single event registration
     * The action must be unique, so it will not be added if it has already been registered.
     * @param action The event keyboard action
     * @param listener The event listener
     * @see Event#Event(KeyboardAction, KeyboardEventListener)
     * @return True if successfully added to the List
     */
    public boolean registerKeyboardEvent(KeyboardAction action, KeyboardEventListener listener) {
        return this.registerKeyboardEvent(new Event(action, listener));
    }

    /**
     * Single event registration
     * The action must be unique, so it will not be added if it has already been registered.
     * @param action The event keyboard action
     * @param listener The event listener
     * @param canKeepPressed Optional argument for event
     * @see Event#Event(KeyboardAction, KeyboardEventListener, boolean)
     * @return True if successfully added to the List
     */
    public boolean registerKeyboardEvent(KeyboardAction action, KeyboardEventListener listener, boolean canKeepPressed) {
        return this.registerKeyboardEvent(new Event(action, listener, canKeepPressed));
    }

    /**
     * Add multiple events linked to one listener, if you want multiple listeners, use the
     * overloaded methods for a list of events instead.
     * Actions must be unique, so it will not be added if it has already been registered.
     * @param actions A list of actions linked to the listener
     * @param listener A Static or Dynamic listener
     * @see this#registerKeyboardEvents(List)
     * @see Event#Event(KeyboardAction, KeyboardEventListener, boolean)
     */
    public void registerKeyboardEvents(List<KeyboardAction> actions, KeyboardEventListener listener) {
        actions.forEach(keyboardAction -> registerKeyboardEvent(keyboardAction, listener));
    }

    /**
     * Add multiple events linked to one listener, if you want multiple listeners, use the
     * overloaded methods for a list of events instead.
     * Actions must be unique, so it will not be added if it has already been registered.
     * @param actions A list of actions linked to the listener
     * @param listener A Static or Dynamic listener
     * @param canKeepPressed Optional argument for event
     * @see Event#Event(KeyboardAction, KeyboardEventListener, boolean)
     * @see this#registerKeyboardEvents(List)
     */
    public void registerKeyboardEvents(List<KeyboardAction> actions, KeyboardEventListener listener, boolean canKeepPressed) {
        actions.forEach(keyboardAction -> registerKeyboardEvent(keyboardAction, listener, canKeepPressed));
    }

    /**
     * Add multiple events linked to their listener
     * Actions must be unique, so it will not be added if it has already been registered.
     * @param events A list of events which all have their sub-properties, we just transfer them
     * @see Event#Event(KeyboardAction, KeyboardEventListener, boolean)
     */
    public void registerKeyboardEvents(List<Event> events) {
        events.forEach(this::registerKeyboardEvent);
    }

    /**
     * Remove an event based on the linked action, because they are unique
     * @param action The action to remove
     * @return True upon successful deletion
     */
    public boolean unregisterKeyboardEvent(KeyboardAction action) {
        return eventList.removeIf(e -> e.getAction().equals(action));
    }

}
