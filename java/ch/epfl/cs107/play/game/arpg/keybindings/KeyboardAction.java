package ch.epfl.cs107.play.game.arpg.keybindings;

import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Keyboard;

public enum KeyboardAction implements Action {
    /* NAME         | DEFAULT KEY    | INTEFACE METHODS (MAINLY TTTLE)                                           */
    MOVE_UP          (Keyboard.UP)    { @Override public String getActionTitle() { return "Move Up"; } },
    MOVE_RIGHT       (Keyboard.RIGHT) { @Override public String getActionTitle() { return "Move Right"; } },
    MOVE_DOWN        (Keyboard.DOWN)  { @Override public String getActionTitle() { return "Move Down"; } },
    MOVE_LEFT        (Keyboard.LEFT)  { @Override public String getActionTitle() { return "Move Left"; } },
    CYCLE_INVENTORY  (Keyboard.TAB)   { @Override public String getActionTitle() { return "Cycle Inventory"; } },
    USE_CURRENT_ITEM (Keyboard.SPACE) { @Override public String getActionTitle() { return "Use"; } },
    VIEW_INTERACTION (Keyboard.E)     { @Override public String getActionTitle() { return "Interact"; } };

    private XMLBindings bindings;
    private int actionKey;

    /**
     * We initialize the keybind using a default key ID if it is not yet initialized in the keybinding
     * savestate, in other words, the keybinds XML file.
     * @param defaultKeyId Default Keyboard key ID
     * @see ch.epfl.cs107.play.game.arpg.keybindings.XMLBindings
     */
    KeyboardAction(int defaultKeyId) {
        this.bindings = new XMLBindings();
        if (!bindings.getBindings().containsKey(this.name())) {
            this.actionKey = defaultKeyId;
            bindings.addBinding(new XMLBinding(this.name(), defaultKeyId));
        } else {
            this.actionKey = bindings.getBinding(this.name()).getKeyId();
        }
    }

    /**
     * Modify the action key, useful, for example, for a settings menu.
     * @param key the new Keyboard key to be set
     */
    public void setActionKey(int key) {
        actionKey = key;
        this.bindings.setBinding(new XMLBinding(this.name(), key));
    }

    @Override
    public Button getAssignedButton(Keyboard areaKeyboard) {
        return areaKeyboard.get(actionKey);
    }
}
