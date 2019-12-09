package ch.epfl.cs107.play.game.arpg.keybindings;

import ch.epfl.cs107.play.window.Keyboard;

import java.util.ArrayList;
import java.util.List;

public class KeyboardEventRegister {

    private List<Event> eventList = new ArrayList<>();
    private Keyboard keyboard;

    public KeyboardEventRegister(Keyboard keyboard) {
        this.keyboard = keyboard;
    }

    public void update() {
        for (Event e: eventList) {
            if (e.getAction().getAssignedButton(keyboard).isPressed())
                e.onKeyEvent();
        }
    }

    public boolean registerKeyboardEvent(Event event) {
        for (Event e: eventList)
            if (e.getAction().equals(event.getAction()))
                return false;
        return eventList.add(event);
    }

    public boolean registerKeyboardEvent(KeyboardAction action, KeyboardEventListener listener) {
        return this.registerKeyboardEvent(new Event(action, listener));
    }

    public boolean unregisterKeyboardEvent(KeyboardAction action) {
        return eventList.removeIf(e -> e.getAction().equals(action));
    }

}
