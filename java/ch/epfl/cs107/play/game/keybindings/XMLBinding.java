package ch.epfl.cs107.play.game.keybindings;

public class XMLBinding {

    private String name;
    private KeyboardKey key;

    /**
     * A pair of two properties to store the data from the XML keybinds
     * @param name Action and Keybind name
     * @param key The Keyboard key ID correpsonding to the action
     * @see KeyboardAction
     */
    XMLBinding(String name, KeyboardKey key) {
        this.name = name;
        this.key = key;
    }

    /**
     * @return The action/keybind name
     */
    public String getName() {
        return name;
    }

    /**
     * @return The key id linked
     */
    public KeyboardKey getKey() {
        return key;
    }

    /**
     * A way to have the linked action rapidly from the data held in the XML
     * @return The keyboard action linked
     */
    public KeyboardAction toAction() {
        return KeyboardAction.valueOf(this.name);
    }

}
