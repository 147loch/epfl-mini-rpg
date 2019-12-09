package ch.epfl.cs107.play.game.arpg.keybindings;

public class Event {

    private KeyboardAction action;
    private KeyboardEventListener listener;

    Event(KeyboardAction action, KeyboardEventListener listener) {
        this.action = action;
        this.listener = listener;
    }

    protected void onKeyEvent() {
        listener.onKeyEvent();
    }

    public KeyboardAction getAction() {
        return action;
    }

}
