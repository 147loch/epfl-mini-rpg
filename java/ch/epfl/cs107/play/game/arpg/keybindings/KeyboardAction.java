package ch.epfl.cs107.play.game.arpg.keybindings;

import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Keyboard;

public enum KeyboardAction implements Action {
    /* NAME               | DEFAULT KEY       | INTEFACE METHODS (MAINLY TTTLE)                                           */
    MOVE_UP                (KeyboardKey.UP)    { @Override public String getActionTitle() { return "Move Up"; } },
    MOVE_RIGHT             (KeyboardKey.RIGHT) { @Override public String getActionTitle() { return "Move Right"; } },
    MOVE_DOWN              (KeyboardKey.DOWN)  { @Override public String getActionTitle() { return "Move Down"; } },
    MOVE_LEFT              (KeyboardKey.LEFT)  { @Override public String getActionTitle() { return "Move Left"; } },
    CYCLE_INVENTORY        (KeyboardKey.TAB)   { @Override public String getActionTitle() { return "Cycle Inventory"; } },
    USE_CURRENT_ITEM       (KeyboardKey.SPACE) { @Override public String getActionTitle() { return "Use"; } },
    VIEW_INTERACTION       (KeyboardKey.E)     { @Override public String getActionTitle() { return "Interact"; } },
    CHEAT_SPAWN_BOMB       (KeyboardKey.B)     { @Override public String getActionTitle() { return "Spawn Bombs"; } },
    CHEAT_SPAWN_FLAMESKULL (KeyboardKey.S)     { @Override public String getActionTitle() { return "Spawn Flame Skull"; } };

    private XMLBindings bindings;
    private KeyboardKey actionKey;

    /**
     * We initialize the keybind using a default key ID if it is not yet initialized in the keybinding
     * savestate, in other words, the keybinds XML file.
     * @param defaultKey Default Keyboard key ID
     * @see XMLBindings
     */
    KeyboardAction(KeyboardKey defaultKey) {
        this.bindings = new XMLBindings();
        if (!bindings.getBindings().containsKey(this.name())) {
            this.actionKey = defaultKey;
            bindings.addBinding(new XMLBinding(this.name(), defaultKey));
        } else {
            this.actionKey = bindings.getBinding(this.name()).getKey();
        }
    }

    /**
     * Modify the action key, useful, for example, for a settings menu.
     * @param key the new Keyboard key to be set
     */
    public void setActionKey(KeyboardKey key) {
        actionKey = key;
        this.bindings.setBinding(new XMLBinding(this.name(), key));
    }

    @Override
    public Button getAssignedButton(Keyboard areaKeyboard) {
        return areaKeyboard.get(actionKey.getCode());
    }
}
